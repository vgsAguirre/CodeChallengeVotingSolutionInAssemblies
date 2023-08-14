package com.desafio.projeto_sicredi.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
@ExtendWith(MockitoExtension.class)
class CpfValidatorTest {

    @Test
    @DisplayName("Teste CPF válido")
    void testValidCpf() {
        assertTrue(CpfValidator.isValid("123.456.789-09"));
    }

    @Test
    @DisplayName("Teste CPF inválido - Tamanho")
    void testInvalidCpf_Length() {
        assertFalse(CpfValidator.isValid("1234567890")); // CPF com tamanho inválido
    }

    @Test
    @DisplayName("Teste CPF inválido - Dígitos Repetidos")
    void testInvalidCpf_RepeatedDigits() {
        assertFalse(CpfValidator.isValid("111.111.111-11")); // CPF com dígitos repetidos
    }

    @Test
    @DisplayName("Teste CPF inválido - Primeiro Dígito Verificador")
    void testInvalidCpf_FirstVerifier() {
        assertFalse(CpfValidator.isValid("123.456.789-01")); // CPF com primeiro dígito verificador inválido
    }

    @Test
    @DisplayName("Teste CPF inválido - Segundo Dígito Verificador")
    void testInvalidCpf_SecondVerifier() {
        assertFalse(CpfValidator.isValid("123.456.789-02")); // CPF com segundo dígito verificador inválido
    }
}
