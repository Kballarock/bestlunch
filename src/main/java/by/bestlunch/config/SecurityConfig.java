package by.bestlunch.config;

import by.bestlunch.security.RestBasicAuthenticationEntryPoint;
import by.bestlunch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@ComponentScan({"by.bestlunch"})
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends GlobalMethodSecurityConfiguration {

    private final UserService userService;

    @Autowired
    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Configuration
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        private final AuthenticationSuccessHandler authSuccessHandler;
        private final AccessDeniedHandler accessDeniedHandler;
        private final LogoutSuccessHandler logoutSuccessHandler;
        private final AuthenticationFailureHandler authFailureHandler;

        @Autowired
        public FormLoginWebSecurityConfigurerAdapter(
                AuthenticationSuccessHandler authSuccessHandler,
                AccessDeniedHandler accessDeniedHandler,
                LogoutSuccessHandler myLogoutSuccessHandler,
                AuthenticationFailureHandler authFailureHandler) {
            this.authSuccessHandler = authSuccessHandler;
            this.accessDeniedHandler = accessDeniedHandler;
            this.logoutSuccessHandler = myLogoutSuccessHandler;
            this.authFailureHandler = authFailureHandler;
        }

        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        public void configure(final WebSecurity web) {
            web.ignoring().antMatchers("/resource/**");
        }

        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/welcome", "/login").permitAll()
                    .antMatchers("/registration").anonymous()
                    .antMatchers("/restaurant", "/profile", "/menu").hasAnyRole("ADMIN", "USER")
                    .antMatchers("/**/admin/**", "/users").hasRole("ADMIN")
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .failureUrl("/login?error=true")
                    .successHandler(authSuccessHandler)
                    .failureHandler(authFailureHandler)
                    .permitAll()
                    .and()
                    .logout()
                    .logoutSuccessHandler(logoutSuccessHandler)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                    .and()
                    .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        }
    }

    @Configuration
    @Order(1)
    public static class RestSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        private final RestBasicAuthenticationEntryPoint authenticationEntryPoint;

        @Autowired
        public RestSecurityConfigurationAdapter(RestBasicAuthenticationEntryPoint authenticationEntryPoint) {
            this.authenticationEntryPoint = authenticationEntryPoint;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .requestMatchers()
                    .antMatchers("/rest/**")
                    .and()
                    .authorizeRequests()
                    .antMatchers("/rest/admin/**").hasRole("ADMIN")
                    .antMatchers("/rest/profile/register").anonymous()
                    .antMatchers("/**").authenticated()
                    .and().csrf().disable()
                    .httpBasic()
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }
}