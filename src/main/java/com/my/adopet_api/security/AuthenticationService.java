package com.my.adopet_api.security;

import com.my.adopet_api.repository.AbrigoRepository;
import com.my.adopet_api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private AbrigoRepository abrigoRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Primeiro, tenta encontrar o usuário como Abrigo
        var abrigo = abrigoRepository.findByEmail(email);
        if (abrigo != null) {
            return abrigo; // Se for encontrado, retorna o Abrigo
        }

        // Caso contrário, tenta encontrar o Tutor
        var tutor = tutorRepository.findByEmail(email);
        if (tutor != null) {
            return tutor; // Se for encontrado, retorna o Tutor
        }

        // Se nenhum for encontrado, lança exceção
        throw new UsernameNotFoundException("Usuário não encontrado");
    }
}
