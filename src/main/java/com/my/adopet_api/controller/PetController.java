package com.my.adopet_api.controller;

import com.my.adopet_api.dto.AtualizarPetDto;
import com.my.adopet_api.dto.InformarPetDto;
import com.my.adopet_api.dto.SalvarPetDto;
import com.my.adopet_api.model.Abrigo;
import com.my.adopet_api.model.Pet;
import com.my.adopet_api.repository.AbrigoRepository;
import com.my.adopet_api.repository.PetsRepository;
import com.my.adopet_api.service.AbrigoService;
import com.my.adopet_api.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetsRepository petsRepository;

    @Autowired
    private AbrigoService abrigoService;

    @Autowired
    private PetService petService;

    @PostMapping
    public ResponseEntity salvarPet(@RequestBody @Valid SalvarPetDto dto, UriComponentsBuilder uriComponentsBuilder){
        var abrigo = abrigoService.buscarAbrigo(dto.abrigo());

        Pet pet = new Pet(dto, abrigo);
        petsRepository.save(pet);

        var uri = uriComponentsBuilder.path("/pets/{id}").buildAndExpand(pet.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<InformarPetDto>> buscarPetsNaoAdotados(Pageable pageable){
        var pets = petService.buscarPetsNaoAdotados(pageable);

        return ResponseEntity.ok(pets);
    }

    @GetMapping("/adotados")
    public ResponseEntity<Page<InformarPetDto>> buscarPetsAdotados(Pageable pageable){
        var pets = petService.buscarPetsAdotados(pageable);

        return ResponseEntity.ok(pets);
    }

    @GetMapping("/{id}")
    public ResponseEntity buscarPet(@PathVariable Long id){
        var pet = petService.buscarPet(id);

        return ResponseEntity.ok(new InformarPetDto(pet));
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizarPet(@RequestBody @Valid AtualizarPetDto dto, @PathVariable Long id, Abrigo abrigo){
        var pet = petService.buscarPet(id);
        pet.atualizarPet(dto, abrigo);
        petsRepository.save(pet);

        return ResponseEntity.ok(new InformarPetDto(pet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarPet(@PathVariable Long id){
        var pet = petService.buscarPet(id);
        petsRepository.delete(pet);

        return ResponseEntity.noContent().build();
    }
}
