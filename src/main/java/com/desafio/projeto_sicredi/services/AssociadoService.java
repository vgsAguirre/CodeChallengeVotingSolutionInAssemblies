package com.desafio.projeto_sicredi.services;

import com.desafio.projeto_sicredi.dtos.AssociadoDto;
import com.desafio.projeto_sicredi.exceptions.CustomException;

import java.util.List;

public interface AssociadoService {
    AssociadoDto cadastrarAssociado(String nome, String cpf);

    List<AssociadoDto> listarAssociados() throws CustomException;

    boolean verificarAssociadosCadastrados();

}
