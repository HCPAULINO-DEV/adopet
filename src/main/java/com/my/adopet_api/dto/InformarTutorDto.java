package com.my.adopet_api.dto;

import com.my.adopet_api.model.Tutor;

public record InformarTutorDto(
        Long id,
        String foto,
        String nome,
        String telefone,
        String cidade,
        String sobre
) {
    public InformarTutorDto(Tutor tutor){
        this(tutor.getId(), tutor.getFoto(), tutor.getNome(), tutor.getTelefone(), tutor.getCidade(), tutor.getSobre());
    }
}
