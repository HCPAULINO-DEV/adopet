package com.my.adopet_api.service;

import com.my.adopet_api.dto.InformarAbrigoDto;
import com.my.adopet_api.model.Abrigo;
import com.my.adopet_api.repository.AbrigoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository abrigoRepository;

    public List<InformarAbrigoDto> buscarAbrigos() {
        List<Abrigo> abrigos = abrigoRepository.findAll();

        if (abrigos.isEmpty()){
            throw new RuntimeException("Nenhum abrigo foi encontrado!");
        }

        return abrigos.stream().map(InformarAbrigoDto::new).toList();
    }

    public Abrigo buscarAbrigo(Long id) {
        var abrigo = abrigoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Abrigo ID: " + id + " não foi encontrado!"));

        return abrigo;
    }
}
