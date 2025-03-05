package com.my.adopet_api.controller;

import com.my.adopet_api.dto.*;
import com.my.adopet_api.model.Abrigo;
import com.my.adopet_api.repository.AbrigoRepository;
import com.my.adopet_api.security.TokenService;
import com.my.adopet_api.service.AbrigoService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/abrigos")
public class AbrigoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AbrigoRepository abrigoRepository;

    @Autowired
    private AbrigoService abrigoService;

    @Autowired
    private PasswordEncoder passwordEncoder; // Injeção do PasswordEncoder

    @PostMapping("/login")
    public ResponseEntity efetuarLogin(@RequestBody @Valid AuthenticationDto dto) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.gerarToken((Abrigo) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenJwtDto(tokenJWT));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity salvarAbrigo(@RequestBody @Valid SalvarAbrigoDto dto, UriComponentsBuilder uriComponentsBuilder) {
        Abrigo abrigo = new Abrigo(dto, passwordEncoder); // Usa o PasswordEncoder injetado
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