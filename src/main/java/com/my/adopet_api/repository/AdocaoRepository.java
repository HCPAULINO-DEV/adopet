package com.my.adopet_api.repository;

import com.my.adopet_api.model.Adocao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdocaoRepository extends JpaRepository<Adocao, Long> {
}
