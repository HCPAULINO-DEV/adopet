package com.my.adopet_api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor // Já garante a injeção automática de dependências
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable()) // Desativa CSRF
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless
                .authorizeHttpRequests(req -> {
                    req.requestMatchers(HttpMethod.POST, "/abrigos/login", "/abrigos/cadastrar").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/tutores/login", "/tutores/cadastrar").permitAll();
                    req.requestMatchers(HttpMethod.GET, "/abrigos/{id}").permitAll();
                    req.anyRequest().authenticated(); // Requer autenticação para outros endpoints
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // Adiciona filtro personalizado
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager(); // Gerenciador de autenticação
    }

    @Bean
    @Primary // Marca este bean como preferido caso haja múltiplos candidatos
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Codificador de senhas seguro
    }
}
