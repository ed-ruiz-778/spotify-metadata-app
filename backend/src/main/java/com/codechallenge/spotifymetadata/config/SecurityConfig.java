package com.codechallenge.spotifymetadata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @Order(1) // Este filtro se ejecuta primero
    public SecurityFilterChain swaggerUiFilterChain(HttpSecurity http) throws Exception {
        // Reglas de seguridad específicas para la documentación de la API
        return http
                .securityMatcher("/swagger-ui/**", "/api-docs", "/api-docs/**")
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/codechallenge/**")
                .csrf(csrf -> csrf.disable())
                // AÑADE ESTA LÍNEA
                .cors(withDefaults())
                .authorizeHttpRequests(authz -> authz.anyRequest().authenticated())
                .httpBasic(withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
