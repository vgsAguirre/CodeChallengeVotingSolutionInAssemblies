package com.desafio.projeto_sicredi.services;

import com.desafio.projeto_sicredi.dtos.SessaoDto;
import com.desafio.projeto_sicredi.entities.Pauta;
import com.desafio.projeto_sicredi.entities.Sessao;
import java.util.List;

public interface SessaoService {

    Sessao abrirSessaoVotacao(Integer duracaoMinutos, Pauta pauta);

    Pauta fecharSessaoVotacao(Sessao sessao);

    List<SessaoDto> listarSessoes();

    List<SessaoDto> listarSessoesPorPauta(Long id);

}
