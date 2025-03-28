package com.spring_boot.spring_boot_rest_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicEndpoints()).permitAll() // public endpoints
                        .requestMatchers("/api/v2/subscriber/**").hasRole("USER") // user role required for user endpoints
                        .requestMatchers("/api/administration/**").hasRole("ADMIN") // admin role required for admin endpoints
                        .anyRequest().authenticated() // secure all other endpoints
                );
        return http.build();
    }

    @Bean
    public String[] publicEndpoints() {
        return new String[]{
                "/api/v2/register",
                "/api/v2/authenticate",
                "/api/v2/delete/{id}",
        };
    }
}

