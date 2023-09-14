package com.desafio.projeto_sicredi.services.impl;

import com.desafio.projeto_sicredi.dtos.VotoDto;
import com.desafio.projeto_sicredi.entities.Associado;
import com.desafio.projeto_sicredi.entities.Pauta;
import com.desafio.projeto_sicredi.entities.Sessao;
import com.desafio.projeto_sicredi.entities.Voto;
import com.desafio.projeto_sicredi.enums.VotoEnum;
import com.desafio.projeto_sicredi.exceptions.CustomException;
import com.desafio.projeto_sicredi.repositories.AssociadoRepository;
import com.desafio.projeto_sicredi.repositories.SessaoRepository;
import com.desafio.projeto_sicredi.repositories.VotoRepository;
import com.desafio.projeto_sicredi.validators.AssociadoValidator;
import com.desafio.projeto_sicredi.validators.CpfValidator;
import com.desafio.projeto_sicredi.validators.VotoValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("VotoServiceImpl Tests")
class VotoServiceImplTest {

    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private AssociadoRepository associadoRepository;

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private VotoValidator validationVoto;

    @Mock
    private AssociadoValidator validationAssociado;

    @InjectMocks
    private VotoServiceImpl votoService;

    /*@Test
    @DisplayName("Test Registrar Voto - Successful")
    void testRegistrarVoto_Success() {
        when(validationAssociado.cpfVazioOuNulo(anyString())).thenReturn(false);
        when(validationAssociado.removerMascaraCPF(anyString())).thenReturn("12345678901");
        when(CpfValidator.isValid(anyString())).thenReturn(true);
        when(validationVoto.associadoJaVotou(anyString())).thenReturn(false);
        when(sessaoRepository.findByStatus(true)).thenReturn(Sessao.builder().build());
        when(associadoRepository.findByCpf(anyString())).thenReturn(Associado.builder().build());

        assertDoesNotThrow(() -> votoService.registrarVoto(VotoEnum.SIM, "12345678901"));

        verify(votoRepository, times(1)).save(any(Voto.class));
    }

    @Test
    @DisplayName("Test Registrar Voto - Invalid CPF")
    void testRegistrarVoto_InvalidCPF() {
        when(validationAssociado.cpfVazioOuNulo(anyString())).thenReturn(false);
        when(validationAssociado.removerMascaraCPF(anyString())).thenReturn("12345678901");
        when(CpfValidator.isValid(anyString())).thenReturn(false);

        assertThrows(CustomException.class, () -> votoService.registrarVoto(VotoEnum.SIM, "12345678901"));
        verify(votoRepository, never()).save(any(Voto.class));
    }

    @Test
    @DisplayName("Test Registrar Voto - Associado Already Voted")
    void testRegistrarVoto_AssociadoAlreadyVoted() {
        when(validationAssociado.cpfVazioOuNulo(anyString())).thenReturn(false);
        when(validationAssociado.removerMascaraCPF(anyString())).thenReturn("12345678901");
        when(CpfValidator.isValid(anyString())).thenReturn(true);
        when(validationVoto.associadoJaVotou(anyString())).thenReturn(true);

        assertThrows(CustomException.class, () -> votoService.registrarVoto(VotoEnum.SIM, "12345678901"));
        verify(votoRepository, never()).save(any(Voto.class));
    }

    @Test
    @DisplayName("Test Registrar Voto - No Open Sessao")
    void testRegistrarVoto_NoOpenSessao() {
        when(validationAssociado.cpfVazioOuNulo(anyString())).thenReturn(false);
        when(validationAssociado.removerMascaraCPF(anyString())).thenReturn("12345678901");
        when(CpfValidator.isValid(anyString())).thenReturn(true);
        when(validationVoto.associadoJaVotou(anyString())).thenReturn(false);
        when(sessaoRepository.findByStatus(true)).thenReturn(Sessao.builder().build());

        assertThrows(CustomException.class, () -> votoService.registrarVoto(VotoEnum.SIM, "12345678901"));
        verify(votoRepository, never()).save(any(Voto.class));
    }

    @Test
    @DisplayName("Test Registrar Voto - Associado Not Found")
    void testRegistrarVoto_AssociadoNotFound() {
        when(validationAssociado.cpfVazioOuNulo(anyString())).thenReturn(false);
        when(validationAssociado.removerMascaraCPF(anyString())).thenReturn("12345678901");
        when(CpfValidator.isValid(anyString())).thenReturn(true);
        when(validationVoto.associadoJaVotou(anyString())).thenReturn(false);
        when(sessaoRepository.findByStatus(true)).thenReturn(Sessao.builder().build());
        when(associadoRepository.findByCpf(anyString())).thenReturn(null);

        assertThrows(CustomException.class, () -> votoService.registrarVoto(VotoEnum.SIM, "12345678901"));
        verify(votoRepository, never()).save(any(Voto.class));
    }

    @Test
    @DisplayName("Test Obter Resultado Votacao - Successful")
    void testObterResultadoVotacao_Success() {
        when(votoRepository.findByPauta(any(Pauta.class)))
                .thenReturn(Arrays.asList(Voto.builder().build(),
                        Voto.builder().build()));
        List<VotoDto> resultado = votoService.obterResultadoVotacao(Pauta.builder().build());
        assertFalse(resultado.isEmpty());
    }

    @Test
    @DisplayName("Test Obter Resultado Votacao - No Result")
    void testObterResultadoVotacao_NoResult() {
        assertThrows(CustomException.class,
                () -> votoService.obterResultadoVotacao(Pauta.builder().build()));
    }*/
}