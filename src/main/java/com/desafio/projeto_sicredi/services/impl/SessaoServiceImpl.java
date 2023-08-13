package com.desafio.projeto_sicredi.services.impl;

import com.desafio.projeto_sicredi.dtos.SessaoDto;
import com.desafio.projeto_sicredi.entities.Pauta;
import com.desafio.projeto_sicredi.entities.Sessao;
import com.desafio.projeto_sicredi.exceptions.CustomException;
import com.desafio.projeto_sicredi.repositories.SessaoRepository;
import com.desafio.projeto_sicredi.services.SessaoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessaoServiceImpl implements SessaoService {

    private final SessaoRepository sessaoRepository;

    private final ModelMapper modelMapper;

    private final Sessao sessao = new Sessao();

    @Override
    public Sessao abrirSessaoVotacao(Integer duracaoMinutos, Pauta pauta) {
        sessao.setTempoSessao(duracaoMinutos);
        sessao.setStatus(Boolean.TRUE);
        sessao.setPauta(pauta);
        return sessaoRepository.save(sessao);
    }

    @Override
    public Pauta fecharSessaoVotacao(Sessao sessao) {

        sessao.setStatus(Boolean.FALSE);
        sessaoRepository.save(sessao);
        return sessao.getPauta();
    }

    @Override
    public List<SessaoDto> listarSessoes() {


            List<Sessao> listSessao = Optional.ofNullable(sessaoRepository.findAll())
                    .orElse(new ArrayList<>());

            if (listSessao.isEmpty()) throw new CustomException(HttpStatus.NOT_FOUND, "Não há sessões cadastradas.");

       try {
            return listSessao.stream()
                    .map(s -> modelMapper.map(sessao, SessaoDto.class))
                    .toList();

        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno.");
        }
    }

    @Override
    public List<SessaoDto> listarSessoesPorPauta(Long id) {

            List<Sessao> sessoes = Optional.ofNullable(sessaoRepository.findByPautaId(id))
                    .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Não encontado nenhuma sessão nessa pauta."));

        try {
            return sessoes.stream()
                    .map(s -> modelMapper.map(sessao, SessaoDto.class))
                    .toList();

        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno.");
        }
    }
}