package com.desafio.projeto_sicredi.validators;

import org.springframework.stereotype.Component;

@Component
public class CpfValidator {
    public static boolean isValid(String cpf) {
        // Remover caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verificar se possui 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verificar se todos os dígitos são iguais
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        int[] digits = cpf.chars().map(Character::getNumericValue).toArray();

        // Verificar o primeiro dígito verificador
        int firstVerifier = calculateVerifier(digits, 9, 10);
        if (digits[9] != firstVerifier) {
            return false;
        }

        // Verificar o segundo dígito verificador
        int secondVerifier = calculateVerifier(digits, 10, 11);
        if (digits[10] != secondVerifier) {
            return false;
        }

        return true;
    }

    private static int calculateVerifier(int[] digits, int start, int end) {
        int sum = 0;
        int weight = start + 1;
        for (int i = 0; i < start; i++) {
            sum += digits[i] * weight;
            weight--;
        }
        int verifier = sum % 11;
        if (verifier < 2) {
            return 0;
        } else {
            return 11 - verifier;
        }
    }
}
