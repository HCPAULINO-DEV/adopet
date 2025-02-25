package com.my.adopet_api.controller;

import com.my.adopet_api.dto.AtualizarTutorDto;
import com.my.adopet_api.dto.InformarTutorDto;
import com.my.adopet_api.dto.SalvarTutorDto;
import com.my.adopet_api.model.Tutor;
import com.my.adopet_api.repository.TutorRepository;
import com.my.adopet_api.service.TutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/tutores")
public class TutorController {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private TutorService tutorService;

    @PostMapping
    public ResponseEntity salvarTutor(@RequestBody @Valid SalvarTutorDto dto, UriComponentsBuilder uriComponentsBuilder){
        Tutor tutor = new Tutor(dto);
        tutorRepository.save(tutor);
        var uri = uriComponentsBuilder.path("/tutores/{id}").buildAndExpand(tutor.getId()).toUri();

        return ResponseEntity.created(uri).body(new InformarTutorDto(tutor));
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizarTutor(@RequestBody @Valid AtualizarTutorDto dto, @PathVariable Long id){
        var tutor = tutorService.buscarTutorPorId(id);
        tutor.atualizarTutor(dto);
        tutorRepository.save(tutor);

        return ResponseEntity.ok().body(new InformarTutorDto(tutor));
    }

    @GetMapping
    public ResponseEntity<Page<InformarTutorDto>> buscarTutores(Pageable pageable){
        var tutores = tutorService.buscarTutores(pageable);

        return ResponseEntity.ok().body(tutores);
    }

    @GetMapping("/{id}")
    public ResponseEntity buscarTutorPorId(@PathVariable Long id){
        var tutor = tutorService.buscarTutorPorId(id);

        return ResponseEntity.ok().body(new InformarTutorDto(tutor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarTutor(@PathVariable Long id){
        var tutor = tutorService.buscarTutorPorId(id);
        tutorRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
