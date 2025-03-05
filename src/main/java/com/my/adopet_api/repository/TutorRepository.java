package com.my.adopet_api.repository;

import com.my.adopet_api.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface TutorRepository extends JpaRepository<Tutor, Long> {
    UserDetails findByEmail(String email);
}
