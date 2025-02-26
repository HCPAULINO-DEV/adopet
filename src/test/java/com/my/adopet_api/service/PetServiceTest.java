package com.my.adopet_api.service;

import com.my.adopet_api.dto.InformarPetDto;
import com.my.adopet_api.dto.SalvarPetDto;
import com.my.adopet_api.model.Abrigo;
import com.my.adopet_api.model.Pet;
import com.my.adopet_api.repository.PetsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @InjectMocks
    private PetService petService;

    @Mock
    private PetsRepository petsRepository;

    @Mock
    private SalvarPetDto dto;

    @Mock
    private Abrigo abrigo;

    @Test
    public void buscarPetsNaoAdotados_PetEncontrado() {
        // Arrange
        Pet pet1 = new Pet(dto, abrigo);
        Pet pet2 = new Pet(dto, abrigo);
        Pageable pageable = PageRequest.of(0, 10);
        List<Pet> pets = List.of(pet1, pet2);  // Certifique-se de que dto e abrigo estão bem definidos
        Page<Pet> petPage = new PageImpl<>(pets, pageable, pets.size());

        BDDMockito.when(petsRepository.findByAdotadoFalse(pageable)).thenReturn(petPage);

        // Act
        Page<InformarPetDto> resultado = petService.buscarPetsNaoAdotados(pageable);

        // Assert
        Assertions.assertNotNull(resultado);
        Assertions.assertFalse(resultado.isEmpty());

    }

    @Test
    public void buscarPetsNaoAdotados_PetNaoEncontrado() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        List<Pet> pets = List.of();
        Page<Pet> petPage = new PageImpl<>(pets, pageable, pets.size());

        BDDMockito.when(petsRepository.findByAdotadoFalse(pageable)).thenReturn(petPage);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> petService.buscarPetsNaoAdotados(pageable));
        Assertions.assertEquals("Nenhum PET encontrado!", exception.getMessage());

    }

    @Test
    public void buscarPetsAdotados_PetsEncontrados(){
        // Arrange
        Pet pet1 = new Pet(dto, abrigo);
        Pet pet2 = new Pet(dto, abrigo);
        Pageable pageable = PageRequest.of(0, 10);
        List<Pet> pets = List.of(pet1, pet2);  // Certifique-se de que dto e abrigo estão bem definidos
        Page<Pet> petPage = new PageImpl<>(pets, pageable, pets.size());

        BDDMockito.when(petsRepository.findByAdotadoTrue(pageable)).thenReturn(petPage);

        // Act
        Page<InformarPetDto> resultado = petService.buscarPetsAdotados(pageable);

        // Assert
        Assertions.assertNotNull(resultado);
        Assertions.assertFalse(resultado.isEmpty());

    }

    @Test
    public void buscarPetsAdotados_PetNaoEncontrado() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        List<Pet> pets = List.of();
        Page<Pet> petPage = new PageImpl<>(pets, pageable, pets.size());

        BDDMockito.when(petsRepository.findByAdotadoTrue(pageable)).thenReturn(petPage);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> petService.buscarPetsAdotados(pageable));
        Assertions.assertEquals("Nenhum PET encontrado!", exception.getMessage());

    }

    @Test
    public void buscarPet_PetNaoEncontrado(){
        Pet pet = new Pet(dto, abrigo);
        BDDMockito.when(petsRepository.findById(pet.getId())).thenReturn(Optional.empty());

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> petService.buscarPet(pet.getId()));
        Assertions.assertEquals("Pet ID: " + pet.getId() + " não foi encontrado!", exception.getMessage());

    }


}