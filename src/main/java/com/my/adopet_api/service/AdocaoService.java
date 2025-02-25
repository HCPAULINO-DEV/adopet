package com.my.adopet_api.service;

import com.my.adopet_api.model.Adocao;
import com.my.adopet_api.repository.AdocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdocaoService {

    @Autowired
    private AdocaoRepository adocaoRepository;

    public Adocao buscarAdocao(Long id){
        var adocao = adocaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adoção ID: " + id + " não foi encontrado"));

        return adocao;
    }
}
