package com.desafio.projeto_sicredi.validators;

import com.desafio.projeto_sicredi.repositories.AssociadoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssociadoValidatorTest {

    @Mock
    private CpfValidator cpfValidator;

    @Mock
    private AssociadoRepository associadoRepository;

    @InjectMocks
    private AssociadoValidator associadoValidator;

    @Test
    @DisplayName("Teste de nome vazio ou nulo")
    void testNomeVazioOuNulo() {
        assertTrue(associadoValidator.nomeVazioOuNulo(null));
        assertTrue(associadoValidator.nomeVazioOuNulo(""));
        assertFalse(associadoValidator.nomeVazioOuNulo("João"));
    }

    @Test
    @DisplayName("Teste de CPF vazio ou nulo")
    void testCpfVazioOuNulo() {
        assertTrue(associadoValidator.cpfVazioOuNulo(null));
        assertTrue(associadoValidator.cpfVazioOuNulo(""));
        assertFalse(associadoValidator.cpfVazioOuNulo("12345678909"));
    }

    @Test
    @DisplayName("Teste de remoção de máscara de CPF")
    void testRemoverMascaraCPF() {
        String cpfComMascara = "123.456.789-09";
        String cpfSemMascara = "12345678909";

        assertEquals(cpfSemMascara, associadoValidator.removerMascaraCPF(cpfComMascara));
    }

    @Test
    @DisplayName("Teste de existência de associado com CPF")
    void testExisteAssociadoComCPF() {
        when(associadoRepository.existsByCpf("12345678909")).thenReturn(true);
        when(associadoRepository.existsByCpf("11111111111")).thenReturn(false);

        assertTrue(associadoValidator.existeAssociadoComCPF("12345678909"));
        assertFalse(associadoValidator.existeAssociadoComCPF("11111111111"));
    }
}