package com.my.adopet_api.controller;

import com.my.adopet_api.dto.*;
import com.my.adopet_api.model.Tutor;
import com.my.adopet_api.repository.TutorRepository;
import com.my.adopet_api.security.TokenService;
import com.my.adopet_api.service.TutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/tutores")
public class TutorController {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private TutorService tutorService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private PasswordEncoder passwordEncoder; // Injeção do PasswordEncoder

    @PostMapping("/login")
    public ResponseEntity efetuarLogin(@RequestBody @Valid AuthenticationDto dto) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.gerarToken((Tutor) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenJwtDto(tokenJWT));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity salvarTutor(@RequestBody @Valid SalvarTutorDto dto, UriComponentsBuilder uriComponentsBuilder){
        Tutor tutor = new Tutor(dto, passwordEncoder);
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
