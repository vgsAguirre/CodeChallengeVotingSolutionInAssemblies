package com.desafio.projeto_sicredi.services.impl;

import com.desafio.projeto_sicredi.dtos.SessaoDto;
import com.desafio.projeto_sicredi.entities.Pauta;
import com.desafio.projeto_sicredi.entities.Sessao;
import com.desafio.projeto_sicredi.exceptions.CustomException;
import com.desafio.projeto_sicredi.repositories.SessaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class SessaoServiceImplTest {

    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SessaoServiceImpl sessaoService;

    @Mock
    private Sessao sessao;

    @Test
    void testAbrirSessaoVotacao() {
        Pauta pauta = Pauta.builder().build();
        Integer duracaoMinutos = 10;

        when(sessaoRepository.save(any())).thenReturn(Sessao
                .builder()
                .tempoSessao(duracaoMinutos)
                .pauta(pauta)
                .build());
        Sessao result = sessaoService.abrirSessaoVotacao(duracaoMinutos, pauta);
        assertNotNull(result);
    }

    @Test
    void testFecharSessaoVotacao() {
        sessao.setStatus(true);

        when(sessaoRepository.save(sessao)).thenReturn(sessao);

        Pauta result = sessaoService.fecharSessaoVotacao(sessao);

        assertFalse(sessao.getStatus());
        assertEquals(sessao.getPauta(), result);
        verify(sessaoRepository, times(1)).save(sessao);
    }

    /*@Test
    void testListarSessoes() {
        List<Sessao> sessoes = new ArrayList<>();
        sessoes.add(sessao);

        when(sessaoRepository.findAll()).thenReturn(sessoes);
        when(modelMapper.map(sessao, SessaoDto.class)).thenReturn(new SessaoDto());

        List<SessaoDto> result = sessaoService.listarSessoes();

        assertEquals(1, result.size());
        verify(sessaoRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(sessao, SessaoDto.class);
    }*/

    /*@Test
    void testListarSessoesPorPauta() {
        Long pautaId = 1L;
        List<Sessao> sessoes = new ArrayList<>();
        sessoes.add(sessao);

        when(sessaoRepository.findByPautaId(pautaId)).thenReturn(sessoes);
        when(modelMapper.map(sessao, SessaoDto.class)).thenReturn(new SessaoDto());

        List<SessaoDto> result = sessaoService.listarSessoesPorPauta(pautaId);

        assertEquals(1, result.size());
        verify(sessaoRepository, times(1)).findByPautaId(pautaId);
        verify(modelMapper, times(1)).map(sessao, SessaoDto.class);
    }*/

    @Test
    void testListarSessoesPorPauta_NenhumaSessaoEncontrada() {
        Long pautaId = 1L;

        when(sessaoRepository.findByPautaId(pautaId)).thenReturn(null);

        assertThrows(CustomException.class, () -> sessaoService.listarSessoesPorPauta(pautaId));

        verify(sessaoRepository, times(1)).findByPautaId(pautaId);
        verify(modelMapper, never()).map(sessao, SessaoDto.class);
    }

    @Test
    void testListarSessoesPorPauta_NenhumaSessaoEncontrada_CustomExceptionMessage() {
        Long pautaId = 1L;

        when(sessaoRepository.findByPautaId(pautaId)).thenReturn(null);

        assertThrows(CustomException.class,
                () -> sessaoService.listarSessoesPorPauta(pautaId));

        verify(sessaoRepository, times(1)).findByPautaId(pautaId);
        verify(modelMapper, never()).map(sessao, SessaoDto.class);
    }
}