package com.my.adopet_api.service;

import com.my.adopet_api.dto.SalvarAbrigoDto;
import com.my.adopet_api.model.Abrigo;
import com.my.adopet_api.repository.AbrigoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

    @InjectMocks
    private AbrigoService abrigoService;

    @Mock
    private AbrigoRepository abrigoRepository;

    @Mock
    private SalvarAbrigoDto dto;

    @Mock
    private Abrigo abrigo1;

    @Mock
    private Abrigo abrigo2;

    @Test
    public void buscarAbrigos_AbrigosEncontrados(){
        List<Abrigo> abrigos = List.of(abrigo1, abrigo2);
        BDDMockito.when(abrigoRepository.findAll()).thenReturn(abrigos);


        Assertions.assertNotNull(abrigoService.buscarAbrigos());
        Assertions.assertFalse(abrigoService.buscarAbrigos().isEmpty());

    }

    @Test
    public void buscarAbrigos_AbrigosNaoEncontrados(){
        List<Abrigo> abrigos = List.of();
        BDDMockito.when(abrigoRepository.findAll()).thenReturn(abrigos);

        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> abrigoService.buscarAbrigos());
        Assertions.assertEquals("Nenhum abrigo foi encontrado!", runtimeException.getMessage());

    }

    @Test
    public void buscarAbrigoPorId_AbrigoEncontrado(PasswordEncoder passwordEncoder){
        Long id = 1L;
        Abrigo abrigo = new Abrigo(dto, passwordEncoder);
        abrigo.setId(id);
        BDDMockito.when(abrigoRepository.findById(abrigo.getId())).thenReturn(Optional.of(abrigo));

        Assertions.assertDoesNotThrow(() -> abrigoService.buscarAbrigo(id));

    }

    @Test
    public void buscarAbrigoPorId_AbrigoNaoEncontrado(PasswordEncoder passwordEncoder){

        Abrigo abrigo = new Abrigo(dto, passwordEncoder);

        BDDMockito.when(abrigoRepository.findById(abrigo.getId())).thenReturn(Optional.empty());

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, ()-> abrigoService.buscarAbrigo(abrigo.getId()));
        Assertions.assertEquals("Abrigo ID: " + abrigo.getId() + " não foi encontrado!", exception.getMessage());

    }

}