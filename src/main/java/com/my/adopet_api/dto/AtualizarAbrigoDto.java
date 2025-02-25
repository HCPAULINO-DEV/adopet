package com.my.adopet_api.dto;

import com.my.adopet_api.model.Endereco;

public record AtualizarAbrigoDto(
        String cnpj,
        String nome,
        Endereco endereco
) {
}
