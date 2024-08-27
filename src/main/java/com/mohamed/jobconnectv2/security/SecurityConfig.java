package com.mohamed.jobconnectv2.security;

import com.mohamed.jobconnectv2.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
           .csrf(AbstractHttpConfigurer::disable)
           .cors(AbstractHttpConfigurer::disable)
           .authorizeHttpRequests(authorizationRegister -> {
               authorizationRegister
                       .requestMatchers("/api/v1/auth", "/api/v1/swagger/swagger-ui.html")
                       .permitAll()
                       .anyRequest()
                       .permitAll();
           })
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(sessionConfigurer -> {
                    sessionConfigurer
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                });

        return http.build();
    }
}
