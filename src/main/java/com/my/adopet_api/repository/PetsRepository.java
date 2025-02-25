package com.my.adopet_api.repository;


import com.my.adopet_api.model.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetsRepository extends JpaRepository<Pet, Long> {
    Page<Pet> findByAdotadoFalse(Pageable pageable);
    Page<Pet> findByAdotadoTrue(Pageable pageable);
}
