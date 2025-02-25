package com.my.adopet_api.service;

import com.my.adopet_api.dto.InformarAbrigoDto;
import com.my.adopet_api.dto.SalvarAbrigoDto;
import com.my.adopet_api.model.Abrigo;
import com.my.adopet_api.repository.AbrigoRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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

        List<InformarAbrigoDto> resultados = abrigoService.buscarAbrigos();

        Assertions.assertNotNull(resultados);
        Assertions.assertFalse(resultados.isEmpty());
    }

    @Test
    public void buscarAbrigoPorId_AbrigoNaoEncontrado(){

        Abrigo abrigo = new Abrigo(dto);

        BDDMockito.when(abrigoRepository.findById(abrigo.getId())).thenReturn(Optional.empty());

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, ()-> abrigoService.buscarAbrigo(abrigo.getId()));
        Assertions.assertEquals("Abrigo ID: " + abrigo.getId() + " não foi encontrado!", exception.getMessage());
    }

}