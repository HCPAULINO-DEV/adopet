package com.my.adopet_api.dto;

import jakarta.validation.constraints.NotBlank;

public record SalvarTutorDto(
        String foto,
        String nome,
        String telefone,
        String cidade,
        String sobre
) {
}
