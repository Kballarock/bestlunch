package by.bestlunch.config;

import by.bestlunch.validation.EmailValidator;
import by.bestlunch.validation.PasswordMatchesValidator;
import by.bestlunch.web.converter.DateTimeFormatters;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.resource.CssLinkResourceTransformer;
import org.springframework.web.servlet.resource.VersionResourceResolver;

import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static by.bestlunch.web.json.JacksonObjectMapper.getMapper;

@Configuration
@EnableWebMvc
@ComponentScan("by.bestlunch.web")
public class WebConfig implements WebMvcConfigurer {

    private static final String RESOLVER_JSP_PREFIX = "/WEB-INF/jsp/";
    private static final String RESOLVER_JSP_SUFFIX = ".jsp";
    private static final Integer RESOLVER_JSP_ORDER = 1;
    static final String ENCODING_UTF_8 = "UTF-8";

    public WebConfig() {
        super();
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp(RESOLVER_JSP_PREFIX, RESOLVER_JSP_SUFFIX);
        registry.order(RESOLVER_JSP_ORDER);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/profile").setViewName("profile");
        registry.addViewController("/restaurant").setViewName("restaurant");
        registry.addViewController("/menu").setViewName("menu");
        registry.addViewController("/users").setViewName("users");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/admin").setViewName("admin");
        registry.addViewController("/registration").setViewName("registration");
        registry.addViewController("/exception").setViewName("exception");
        registry.addViewController("/403").setViewName("403");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateTimeFormatters.LocalDateFormatter());
        registry.addFormatter(new DateTimeFormatters.LocalTimeFormatter());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return getMapper();
    }

    @Bean
    public StringHttpMessageConverter responseBodyConverter() {
        return new StringHttpMessageConverter(Charset.forName(ENCODING_UTF_8));
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper());
        converters.add(converter);
        converters.add(responseBodyConverter());
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("resources/**")
                .addResourceLocations("/resources/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS))
                .resourceChain(false)
                .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"))
                .addTransformer(new CssLinkResourceTransformer());
        registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
    }

    @Bean(name = "messageSource")
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding(ENCODING_UTF_8);
        messageSource.setUseCodeAsDefaultMessage(false);
        messageSource.setCacheMillis(5);
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);
    }

    @Bean(name = "localeResolver")
    public LocaleResolver localeResolver() {
        final CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("en"));
        localeResolver.setCookieMaxAge(24 * 60 * 60);
        return localeResolver;
    }

    @Bean
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        return validator;
    }

    @Bean
    public EmailValidator emailValidator() {
        return new EmailValidator();
    }

    @Bean
    public PasswordMatchesValidator passwordMatchesValidator() {
        return new PasswordMatchesValidator();
    }

    @Bean
    public AuthenticationPrincipalArgumentResolver authPrincipalArgumentResolver() {
        return new AuthenticationPrincipalArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(authPrincipalArgumentResolver());
    }
}