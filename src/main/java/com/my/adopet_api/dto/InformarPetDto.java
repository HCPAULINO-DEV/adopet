package com.my.adopet_api.dto;

import com.my.adopet_api.model.Pet;

public record InformarPetDto(
        Long id,
        String nome,
        String raca,
        String cor,
        int idade,
        Float peso,
        Long abrigo,
        Boolean adotado
) {
    public InformarPetDto(Pet pet){
        this(pet.getId(), pet.getNome(), pet.getRaca(), pet.getCor(), pet.getIdade(), pet.getPeso(), pet.getAbrigo().getId(), pet.getAdotado());
    }
}
