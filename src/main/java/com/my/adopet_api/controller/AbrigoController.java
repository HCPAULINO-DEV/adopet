package com.my.adopet_api.controller;

import com.my.adopet_api.dto.AtualizarAbrigoDto;
import com.my.adopet_api.dto.InformarAbrigoDto;
import com.my.adopet_api.dto.InformarTutorDto;
import com.my.adopet_api.dto.SalvarAbrigoDto;
import com.my.adopet_api.model.Abrigo;
import com.my.adopet_api.repository.AbrigoRepository;
import com.my.adopet_api.service.AbrigoService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/abrigos")
public class AbrigoController {

    @Autowired
    private AbrigoRepository abrigoRepository;

    @Autowired
    private AbrigoService abrigoService;

    @PostMapping
    public ResponseEntity salvarAbrigo(@RequestBody @Valid SalvarAbrigoDto dto, UriComponentsBuilder uriComponentsBuilder){
        Abrigo abrigo = new Abrigo(dto);
        abrigoRepository.save(abrigo);

        var uri = uriComponentsBuilder.path("/abrigos/{id}").buildAndExpand(abrigo.getId()).toUri();

        return ResponseEntity.created(uri).body(new InformarAbrigoDto(abrigo));
    }

    @GetMapping
    public ResponseEntity<List<InformarAbrigoDto>> buscarAbrigos(){
        var abrigos = abrigoService.buscarAbrigos();

        return ResponseEntity.ok().body(abrigos);
    }

    @GetMapping("/{id}")
    public ResponseEntity buscarAbrigoPorId(@PathVariable Long id){
        var abrigo = abrigoService.buscarAbrigo(id);

        return ResponseEntity.ok(new InformarAbrigoDto(abrigo));
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizarAbrigo(@RequestBody @Valid AtualizarAbrigoDto dto, @PathVariable Long id){
        var abrigo = abrigoService.buscarAbrigo(id);
        abrigo.atualizarAbrigo(dto);

        return ResponseEntity.ok().body(new InformarAbrigoDto(abrigo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarAbrigo(@PathVariable Long id){
        var abrigo = abrigoService.buscarAbrigo(id);
        abrigoRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
