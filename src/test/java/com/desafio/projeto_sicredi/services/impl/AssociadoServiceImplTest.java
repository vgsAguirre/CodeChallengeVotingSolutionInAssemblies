package com.desafio.projeto_sicredi.services;

import com.desafio.projeto_sicredi.dtos.AssociadoDto;
import com.desafio.projeto_sicredi.entities.Associado;
import com.desafio.projeto_sicredi.exceptions.CustomException;
import com.desafio.projeto_sicredi.exceptions.ErrorResponse;
import com.desafio.projeto_sicredi.repositories.AssociadoRepository;
import com.desafio.projeto_sicredi.services.impl.AssociadoServiceImpl;
import com.desafio.projeto_sicredi.validators.AssociadoValidator;
import com.desafio.projeto_sicredi.validators.CpfValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssociadoServiceTest {
    @Mock
    private AssociadoRepository associadoRepository;

    @Mock
    private AssociadoValidator validationAssociado;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AssociadoServiceImpl associadoService;

    @Test
    @DisplayName("Teste do método cadastrarAssociado - CPF válido e inexistente")
    void testCadastrarAssociadoWithValidCPFAndNotExisting() {

        String nome = "João";
        String cpf = "01303935090";
        when(validationAssociado.removerMascaraCPF(cpf)).thenReturn(cpf);
        when(validationAssociado.existeAssociadoComCPF(cpf)).thenReturn(false);
        //when(validationAssociado.validaCpf(cpf)).thenReturn(true);
        Associado associadoSalvo = Associado.builder()
                .id(1L)
                .nome(nome)
                .cpf(cpf)
                .build();
        when(associadoRepository.save(any(Associado.class))).thenReturn(associadoSalvo);
        AssociadoDto associadoDto = new AssociadoDto(1L, nome, cpf);
        when(modelMapper.map(associadoSalvo, AssociadoDto.class)).thenReturn(associadoDto);
        AssociadoDto result = associadoService.cadastrarAssociado(nome, cpf);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(nome, result.getNome());
        assertEquals(cpf, result.getCpf());
    }

    @Test
    @DisplayName("Teste do método cadastrarAssociado - CPF inválido")
    void testCadastrarAssociadoWithInvalidCPF() {

        String nome = "João";
        String cpf = "12345678901";
        when(validationAssociado.removerMascaraCPF(cpf)).thenReturn(cpf);
        assertThrows(CustomException.class, () -> associadoService.cadastrarAssociado(nome, cpf));
    }

    @Test
    @DisplayName("Teste do método cadastrarAssociado - CPF já existente")
    void testCadastrarAssociadoWithExistingCPF() {

        String nome = "João";
        String cpf = "01303935090";
        when(validationAssociado.removerMascaraCPF(cpf)).thenReturn(cpf);
        when(validationAssociado.existeAssociadoComCPF(cpf)).thenReturn(true);
        assertThrows(CustomException.class, () -> associadoService.cadastrarAssociado(nome, cpf));
    }

    @Test
    @DisplayName("Teste do método listarAssociados - Lista não vazia")
    void testListarAssociadosWithNonEmptyList() {

        List<Associado> listAssociado = new ArrayList<>();
        listAssociado.add(Associado.builder().build());
        listAssociado.add(Associado.builder().build());
        when(associadoRepository.findAll()).thenReturn(listAssociado);
        AssociadoDto associadoDto = new AssociadoDto();
        when(modelMapper.map(any(Associado.class), any())).thenReturn(associadoDto);
        List<AssociadoDto> result = associadoService.listarAssociados();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Teste do método listarAssociados - Lista vazia")
    void testListarAssociadosWithEmptyList() {

        when(associadoRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(CustomException.class, () -> associadoService.listarAssociados());
    }

    @Test
    @DisplayName("Teste do método verificarAssociadosCadastrados - Com associados cadastrados")
    void testVerificarAssociadosCadastradosWithAssociados() {

        when(associadoRepository.existsAllAssociados()).thenReturn(Boolean.TRUE);
        boolean result = associadoService.verificarAssociadosCadastrados();
        assertTrue(result);
    }

    @Test
    @DisplayName("Teste do método verificarAssociadosCadastrados - Sem associados cadastrados")
    void testVerificarAssociadosCadastradosWithoutAssociados() {

        when(associadoRepository.existsAllAssociados()).thenReturn(false);
        boolean result = associadoService.verificarAssociadosCadastrados();
        assertFalse(result);
    }

    @Test
    @DisplayName("Teste do método verificarAssociadosCadastrados - INTERNAL_SERVER_ERROR")
    void testVerificarAssociadosCadastradosInternalServerError() {
        when(associadoRepository.existsAllAssociados()).thenThrow(new RuntimeException());

        CustomException exception = assertThrows(CustomException.class, () ->
                associadoService.verificarAssociadosCadastrados()
        );

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getHttpStatus());
        assertEquals(AssociadoServiceImpl.INTERNAL_ERROR_MESSAGE, exception.getMessage());
    }

}