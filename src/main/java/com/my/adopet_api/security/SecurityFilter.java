package com.my.adopet_api.security;

import com.my.adopet_api.repository.AbrigoRepository;
import com.my.adopet_api.repository.TutorRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AbrigoRepository abrigoRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT);

            // Primeiro tenta encontrar no Abrigo
            var abrigo = abrigoRepository.findByEmail(subject);
            if (abrigo != null) {
                var authentication = new UsernamePasswordAuthenticationToken(abrigo, null, abrigo.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // Se não encontrar no Abrigo, tenta encontrar no Tutor
                var tutor = tutorRepository.findByEmail(subject);
                if (tutor != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(tutor, null, tutor.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader  = request.getHeader("Authorization");
        if (authorizationHeader  != null){
            return authorizationHeader .replace("Bearer ", "").trim();
        }

        return null;

    }
}
