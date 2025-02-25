package com.my.adopet_api.dto;


import com.my.adopet_api.model.Status;

public record SalvarAdocaoDto(
        Long pet,
        Long abrigo,
        Status status,
        String descricao
) {
}
