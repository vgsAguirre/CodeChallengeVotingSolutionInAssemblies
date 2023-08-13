package com.desafio.projeto_sicredi.services;

import com.desafio.projeto_sicredi.dtos.PautaDto;
import com.desafio.projeto_sicredi.dtos.ResultadoVotacaoDto;
import java.util.List;

public interface PautaService {

    void cadastrarPauta(String titulo, String descricao, Integer tempoSessao);
    ResultadoVotacaoDto obterResultadoVotacao(Long pautaId);
    List<PautaDto> listarPautas();
}
