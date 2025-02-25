package com.my.adopet_api.dto;

public record AtualizarPetDto(
        String nome,
        String raca,
        String cor,
        int idade,
        Float peso,
        Long abrigo,
        Boolean adotado
) {
}
