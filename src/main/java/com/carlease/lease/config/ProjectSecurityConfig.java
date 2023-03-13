package com.carlease.lease.config;

import com.carlease.lease.filter.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .disable()
                .csrf(
                        (csrf) ->
                                csrf.csrfTokenRequestHandler(requestHandler)
                                        .ignoringRequestMatchers("/contact")
                                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
                .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/car-lease/createLease").hasRole("COMPANY")
                .requestMatchers("/api/v1/car-lease/calculate_lease/customer/**").hasAnyRole("BROKER", "COMPANY")
                .requestMatchers("/api/v1/car-lease/calculate_lease/car/**").hasRole("COMPANY")
                .requestMatchers( "/register").permitAll()
                .requestMatchers("/user").authenticated()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/swagger-ui/**")).permitAll()
                .and()
                .csrf().ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
                .and()
                .csrf().ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/swagger-ui/**"))
                .and()
                .headers(headers -> headers.frameOptions().sameOrigin())
                .formLogin()
                .and().httpBasic();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
