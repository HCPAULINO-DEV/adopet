package com.my.adopet_api.service;

import com.my.adopet_api.dto.SalvarAdocaoDto;
import com.my.adopet_api.model.Abrigo;
import com.my.adopet_api.model.Adocao;
import com.my.adopet_api.model.Pet;
import com.my.adopet_api.repository.AdocaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;



@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {

    @InjectMocks
    private AdocaoService adocaoService;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private SalvarAdocaoDto dto;

    @Mock
    private Pet pet;

    @Mock
    private Abrigo abrigo;

    @Test
    public void buscarAdocao_AdocaoEncontrada(){
        Long id = 1L;
        Adocao adocao = new Adocao(dto, pet, abrigo);
        adocao.setId(id);

        BDDMockito.when(adocaoRepository.findById(id)).thenReturn(Optional.of(adocao));

        Assertions.assertDoesNotThrow(() -> adocaoService.buscarAdocao(id));

    }

    @Test
    public void buscarAdocao_AdocaoNaoEncontrada(){
        Adocao adocao = new Adocao(dto, pet, abrigo);

        BDDMockito.when(adocaoRepository.findById(adocao.getId())).thenReturn(Optional.empty());

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> adocaoService.buscarAdocao(adocao.getId()));
        Assertions.assertEquals("Adoção ID: " + adocao.getId() + " não foi encontrado", exception.getMessage());

    }

}