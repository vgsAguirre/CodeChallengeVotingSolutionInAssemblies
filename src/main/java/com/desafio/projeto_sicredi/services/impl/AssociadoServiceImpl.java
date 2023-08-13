package com.desafio.projeto_sicredi.services.impl;

import com.desafio.projeto_sicredi.dtos.AssociadoDto;
import com.desafio.projeto_sicredi.entities.Associado;
import com.desafio.projeto_sicredi.exceptions.CustomException;
import com.desafio.projeto_sicredi.repositories.AssociadoRepository;
import com.desafio.projeto_sicredi.services.AssociadoService;
import com.desafio.projeto_sicredi.validators.AssociadoValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssociadoServiceImpl implements AssociadoService {

    private final AssociadoRepository associadoRepository;
    private final AssociadoValidator validationAssociado;
    private final ModelMapper modelMapper;
    public static final String INTERNAL_ERROR_MESSAGE = "Erro interno.";

    @Override
    public AssociadoDto cadastrarAssociado(String nome, String cpf) {

        if (validationAssociado.nomeVazioOuNulo(nome) || validationAssociado.cpfVazioOuNulo(cpf))
            throw new CustomException(HttpStatus.BAD_REQUEST, "Nome ou Cpf não podem ser vazios.");

        if (!cpf.isEmpty())
            validationAssociado.removerMascaraCPF(cpf);

        if (validationAssociado.existeAssociadoComCPF(cpf)) {
            throw new CustomException(HttpStatus.CONFLICT, "Já existe um associado com esse cpf.");
        }

        if (!validationAssociado.validaCpf(cpf)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Cpf não é válido.");
        }

        Associado associado = Associado.builder()
                .nome(nome)
                .cpf(cpf)
                .build();


        Associado associadoSalvo = associadoRepository.save(associado);
        try {
            return modelMapper.map(associadoSalvo, AssociadoDto.class);

        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_ERROR_MESSAGE);
        }
    }

    @Override
    public List<AssociadoDto> listarAssociados() {


        List<Associado> listAssociado = Optional.ofNullable(associadoRepository.findAll())
                .orElse(new ArrayList<>());

        if (listAssociado.isEmpty()) throw new CustomException(HttpStatus.NOT_FOUND, "Não há associados cadastrados.");

        try {
            return listAssociado.stream()
                    .map(associado -> modelMapper
                            .map(associado, AssociadoDto.class))
                    .collect(Collectors.toList());

            }catch (Exception e){
                throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_ERROR_MESSAGE);
            }
    }
    @Override
    public boolean verificarAssociadosCadastrados () {
        try {
            return associadoRepository.existsAllAssociados();
        }catch (Exception e){
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_ERROR_MESSAGE);
        }
    }
}
