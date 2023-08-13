package com.desafio.projeto_sicredi.dtos;

import com.desafio.projeto_sicredi.enums.VotoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VotoDto {

    private Long id;
    private VotoEnum votoEnum;
    private AssociadoDto associado;
    private PautaDto pauta;
}
