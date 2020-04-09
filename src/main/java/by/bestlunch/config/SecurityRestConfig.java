package by.bestlunch.config;

import by.bestlunch.security.RestBasicAuthenticationEntryPoint;
import by.bestlunch.security.filter.CustomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@ComponentScan({"by.bestlunch"})
@Order(2)
public class SecurityRestConfig extends WebSecurityConfigurerAdapter {

    private final RestBasicAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    public SecurityRestConfig(RestBasicAuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("admin@admin.com")
                .password(passwordEncoder().encode("admin"))
                .authorities("ROLE_ADMIN");

        auth
                .inMemoryAuthentication()
                .withUser("user@user.com")
                .password(passwordEncoder().encode("user"))
                .authorities("ROLE_USER");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/rest/admin/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and().csrf().disable();

        http.addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class);
    }
}