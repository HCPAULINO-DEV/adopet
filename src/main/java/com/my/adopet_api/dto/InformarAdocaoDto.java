package com.my.adopet_api.dto;

import com.my.adopet_api.model.Adocao;
import com.my.adopet_api.model.Status;

public record InformarAdocaoDto(
        Long id,
        Long pet,
        Long abrigo,
        Status status,
        String descricao
) {
    public InformarAdocaoDto(Adocao adocao){
        this(adocao.getId(), adocao.getPet().getId(), adocao.getAbrigo().getId(), adocao.getStatus(), adocao.getDescricao());
    }
}
