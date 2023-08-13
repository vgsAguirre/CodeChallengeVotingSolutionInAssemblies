package com.desafio.projeto_sicredi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultadoVotacaoDto {

    private Long totalVotos = 0L;
    private Long votosSim = 0L;
    private Long votosNao = 0L;
    private String aprovado = "Votação não iniciada.";

}
