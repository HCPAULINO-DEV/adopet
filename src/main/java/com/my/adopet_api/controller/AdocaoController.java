package com.my.adopet_api.controller;

import com.my.adopet_api.dto.InformarAdocaoDto;
import com.my.adopet_api.dto.SalvarAdocaoDto;
import com.my.adopet_api.model.Abrigo;
import com.my.adopet_api.model.Adocao;
import com.my.adopet_api.model.Pet;
import com.my.adopet_api.repository.AbrigoRepository;
import com.my.adopet_api.repository.AdocaoRepository;
import com.my.adopet_api.repository.PetsRepository;
import com.my.adopet_api.service.AbrigoService;
import com.my.adopet_api.service.AdocaoService;
import com.my.adopet_api.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/adocoes")
public class AdocaoController {

    @Autowired
    private AdocaoRepository adocaoRepository;

    @Autowired
    private AdocaoService adocaoService;

    @Autowired
    private PetService petService;

    @Autowired
    private AbrigoService abrigoService;

    @PostMapping
    public ResponseEntity salvarAdocao(@RequestBody @Valid SalvarAdocaoDto dto, UriComponentsBuilder uriComponentsBuilder) {
        Pet pet = petService.buscarPet(dto.pet());
        Abrigo abrigo = abrigoService.buscarAbrigo(dto.abrigo());

        Adocao adocao = new Adocao(dto, pet, abrigo);
        adocaoRepository.save(adocao);
        pet.setAdotadoTrue();

        var uri = uriComponentsBuilder.path("/adocoes/{id}").buildAndExpand(adocao.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarAdocao(@PathVariable Long id){
        var adocao = adocaoService.buscarAdocao(id);
        adocaoRepository.delete(adocao);

        return ResponseEntity.ok(new InformarAdocaoDto(adocao));

    }
}
