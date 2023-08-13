package com.desafio.projeto_sicredi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssociadoDto {

    private Long id;
    private String nome;
    private String cpf;
}
