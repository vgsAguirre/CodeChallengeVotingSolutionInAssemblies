package com.desafio.projeto_sicredi.validators;

import com.desafio.projeto_sicredi.repositories.VotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VotoValidator {

    private final VotoRepository votoRepository;

    public boolean associadoJaVotou(String cpf) {
        return votoRepository.existsByAssociadoCpf(cpf);
    }

}
