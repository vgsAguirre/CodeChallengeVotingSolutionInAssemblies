package com.desafio.projeto_sicredi.services.impl;

import com.desafio.projeto_sicredi.entities.Pauta;
import com.desafio.projeto_sicredi.entities.Sessao;
import com.desafio.projeto_sicredi.exceptions.CustomException;
import com.desafio.projeto_sicredi.repositories.AssociadoRepository;
import com.desafio.projeto_sicredi.repositories.PautaRepository;
import com.desafio.projeto_sicredi.repositories.SessaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PautaServiceImplTest {

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private AssociadoServiceImpl associadoService;

    @Mock
    private SessaoServiceImpl sessaoService;

    @Mock
    private VotoServiceImpl votoService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private WebClient webClient;

    @Mock
    private AssociadoRepository associadoRepository;

    @InjectMocks
    private PautaServiceImpl pautaService;

    @Test
    @DisplayName("Test Cadastrar Pauta - Successful")
    void testCadastrarPauta_Success() {

        String titulo = "Pauta Test";
        String descricao = "Description of pauta";
        Integer tempoSessao = 1;

        when(associadoService.verificarAssociadosCadastrados()).thenReturn(true);
        when(sessaoRepository.existsByStatusIsTrue()).thenReturn(false);
        when(sessaoService.abrirSessaoVotacao(any(), any())).thenReturn(Sessao.builder().build());
        assertDoesNotThrow(() -> pautaService.cadastrarPauta(titulo, descricao, tempoSessao));

        verify(sessaoRepository, times(1)).existsByStatusIsTrue();
        verify(sessaoService, times(1)).abrirSessaoVotacao(eq(tempoSessao), any(Pauta.class));
    }

    @Test
    @DisplayName("Test Cadastrar Pauta - No Associados")
    void testCadastrarPauta_NoAssociados() {

        String titulo = "Pauta Test";
        String descricao = "Description of pauta";
        Integer tempoSessao = 30;

        when(associadoService.verificarAssociadosCadastrados()).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class, () ->
                pautaService.cadastrarPauta(titulo, descricao, tempoSessao));

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }
}