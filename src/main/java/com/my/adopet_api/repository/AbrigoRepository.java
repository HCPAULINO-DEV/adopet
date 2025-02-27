package com.my.adopet_api.repository;

import com.my.adopet_api.model.Abrigo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface AbrigoRepository extends JpaRepository<Abrigo, Long> {
    UserDetails findByEmail(String email);
}
