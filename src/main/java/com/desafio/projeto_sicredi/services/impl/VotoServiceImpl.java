package com.desafio.projeto_sicredi.services.impl;

import com.desafio.projeto_sicredi.dtos.VotoDto;
import com.desafio.projeto_sicredi.entities.Associado;
import com.desafio.projeto_sicredi.entities.Pauta;
import com.desafio.projeto_sicredi.entities.Sessao;
import com.desafio.projeto_sicredi.entities.Voto;
import com.desafio.projeto_sicredi.enums.VotoEnum;
import com.desafio.projeto_sicredi.exceptions.CustomException;
import com.desafio.projeto_sicredi.repositories.AssociadoRepository;
import com.desafio.projeto_sicredi.repositories.SessaoRepository;
import com.desafio.projeto_sicredi.repositories.VotoRepository;
import com.desafio.projeto_sicredi.services.VotoService;
import com.desafio.projeto_sicredi.validators.AssociadoValidator;
import com.desafio.projeto_sicredi.validators.VotoValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VotoServiceImpl implements VotoService {

    private final SessaoRepository sessaoRepository;
    private final AssociadoRepository associadoRepository;
    private final VotoRepository votoRepository;
    private final ModelMapper modelMapper;
    private final VotoValidator validationVoto;
    private final AssociadoValidator validationAssociado;

    @Override
    public void registrarVoto(VotoEnum votoEnum, String cpfAssociado) {

            if (validationAssociado.cpfVazioOuNulo(cpfAssociado)) throw new CustomException(HttpStatus.BAD_REQUEST, "CPF não pode ser vazio ou nulo.");

            if (!validationAssociado.validaCpf(cpfAssociado)) throw new CustomException(HttpStatus.BAD_REQUEST, "CPF inválido.");

            cpfAssociado = validationAssociado.removerMascaraCPF(cpfAssociado);

            if (validationVoto.associadoJaVotou(cpfAssociado)){
                throw new CustomException(HttpStatus.CONFLICT, "Associado já votou nessa pauta.");
            }

            Sessao sessao = Optional.ofNullable(sessaoRepository.findByStatus(Boolean.TRUE))
                    .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Não existe uma sessão aberta."));

            Associado associado = Optional.ofNullable(associadoRepository.findByCpf(cpfAssociado))
                    .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Associado não cadastrado para efetuar a votação."));

            Voto votoBuilder = Voto.builder()
                    .votoEnum(votoEnum)
                    .associado(associado)
                    .pauta(sessao.getPauta())
                    .build();

        try {
            votoRepository.save(votoBuilder);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno.");
        }
    }

    @Override
    public List<VotoDto> obterResultadoVotacao(Pauta pauta) {

            List<Voto> listVoto = Optional.ofNullable(votoRepository.findByPauta(pauta))
                    .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Não foi encontrado nenhum voto."));

        try {
            return listVoto.stream()
                    .map(voto -> modelMapper
                            .map(voto, VotoDto.class))
                    .toList();

        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno.");
        }
    }
}
