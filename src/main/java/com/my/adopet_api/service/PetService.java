package com.my.adopet_api.service;

import com.my.adopet_api.dto.InformarPetDto;
import com.my.adopet_api.model.Pet;
import com.my.adopet_api.repository.PetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PetService {

    @Autowired
    private PetsRepository petsRepository;

    public Page<InformarPetDto> buscarPetsNaoAdotados(Pageable pageable){
        Page<Pet> page = petsRepository.findByAdotadoFalse(pageable);
        Page<InformarPetDto> dto = page.map(InformarPetDto::new);

        if (page.isEmpty()){
            throw new RuntimeException("Nenhum PET encontrado!");
        }

        return dto;

    }

    public Page<InformarPetDto> buscarPetsAdotados(Pageable pageable){
        Page<Pet> page = petsRepository.findByAdotadoTrue(pageable);
        Page<InformarPetDto> dto = page.map(InformarPetDto::new);

        if (page.isEmpty()){
            throw new RuntimeException("Nenhum PET encontrado!");
        }

        return dto;

    }

    public Pet buscarPet(Long id){
        var pet = petsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet ID: " + id + " não foi encontrado!"));

        return pet;
    }
}
