package org.test.springbootgroovytest.global.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import org.test.springbootgroovytest.global.web.GlobalExceptionHandler
import org.test.springbootgroovytest.users.domain.UserRepository

@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfigurerImpl extends WebSecurityConfigurerAdapter {

    @Autowired
    UserRepository userRepository

    @Autowired
    GlobalExceptionHandler exceptionHandler

    @Value('${secret-key}')
    String secretKey

    @Override
    void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new UserAuthenticationProvider(userRepository))
    }

    TokenAuthenticationFilter apiTokenAuthenticationFilter() throws Exception {
        TokenAuthenticationFilter authenticationTokenFilter = new TokenAuthenticationFilter(exceptionHandler,
                tokenProvider())
        authenticationTokenFilter.setAuthenticationManager(authenticationManager())
        authenticationTokenFilter.setAuthenticationSuccessHandler(
                new TokenAuthenticationSuccessHandler())
        return authenticationTokenFilter
    }

    @Bean
    FilterRegistrationBean corsFilter() {
        CorsConfiguration config = new CorsConfiguration()
        config.setAllowCredentials(true)
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource()

        source.registerCorsConfiguration("/**", config)
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source))
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE)
        return bean
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        //disable page caching
        http.headers().cacheControl()

        //disable frame options headers for h2-console
        http.headers().frameOptions().disable()

        http
        //we use tokens as security mechanism
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/configuration/ui/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/health").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/info").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated().and()
        //avoid creating a session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(
                apiTokenAuthenticationFilter(), AbstractPreAuthenticatedProcessingFilter.class)
    }

    @Bean
    TokenProvider tokenProvider() {
        new TokenProvider(secretKey)
    }
}
