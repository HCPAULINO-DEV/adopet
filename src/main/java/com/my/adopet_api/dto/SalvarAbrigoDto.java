package com.my.adopet_api.dto;

import com.my.adopet_api.model.Endereco;

public record SalvarAbrigoDto(
        String cnpj,
        String nome,
        Endereco endereco
) {
}
