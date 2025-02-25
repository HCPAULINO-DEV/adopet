package com.my.adopet_api.model;

import jakarta.persistence.Embeddable;

@Embeddable
public record Endereco(
        String rua,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        String cep
) {
}
