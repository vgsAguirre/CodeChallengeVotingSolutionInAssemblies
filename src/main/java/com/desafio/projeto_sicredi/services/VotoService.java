package com.desafio.projeto_sicredi.services;

import com.desafio.projeto_sicredi.dtos.VotoDto;
import com.desafio.projeto_sicredi.entities.Pauta;
import com.desafio.projeto_sicredi.enums.VotoEnum;
import java.util.List;

public interface VotoService {

    void registrarVoto(VotoEnum voto , String cpfAssociado);

    List<VotoDto> obterResultadoVotacao(Pauta pauta);

}