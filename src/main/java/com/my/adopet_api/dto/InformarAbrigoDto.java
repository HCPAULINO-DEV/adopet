package com.my.adopet_api.dto;

import com.my.adopet_api.model.Abrigo;
import com.my.adopet_api.model.Endereco;
import com.my.adopet_api.repository.AbrigoRepository;

public record InformarAbrigoDto(
        Long id,
        String cnpj,
        String nome,
        Endereco endereco
) {
    public InformarAbrigoDto(Abrigo abrigo){
        this(abrigo.getId(), abrigo.getCnpj(), abrigo.getNome(), abrigo.getEndereco());
    }
}
