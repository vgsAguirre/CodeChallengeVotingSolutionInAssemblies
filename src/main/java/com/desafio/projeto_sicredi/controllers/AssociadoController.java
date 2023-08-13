package com.desafio.projeto_sicredi.controllers;

import com.desafio.projeto_sicredi.dtos.AssociadoDto;
import com.desafio.projeto_sicredi.services.impl.AssociadoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/associados")
@Tag(name = "Associados", description = "API para gerenciar associados")
public class AssociadoController {

    private final AssociadoServiceImpl associadoService;

    public AssociadoController(AssociadoServiceImpl associadoService) {
        this.associadoService = associadoService;
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar Associado", description = "Endpoint para cadastrar um novo associado.")
    @ApiResponse(responseCode = "201", description = "Associado cadastrado com sucesso.")
    @ApiResponse(responseCode = "400", description = "Nome ou Cpf não podem ser vazios.")
    @ApiResponse(responseCode = "400", description = "Cpf não é válido.")
    @ApiResponse(responseCode = "409", description = "Já existe um associado com esse cpf.")
    @ApiResponse(responseCode = "500", description = "Erro interno.")

    public ResponseEntity<String> cadastrarAssociado(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpf) {
            associadoService.cadastrarAssociado(nome, cpf);
        return ResponseEntity.status(HttpStatus.CREATED).body("Associado cadastrado com sucesso.");
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar Associados", description = "Endpoint para listar todos os associados cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de associados retornada com sucesso.")
    @ApiResponse(responseCode = "404", description = "Não há associados cadastrados.")
    @ApiResponse(responseCode = "500", description = "Erro interno.")
     public ResponseEntity<List<AssociadoDto>> listarAssociados() {
        List<AssociadoDto> associados = associadoService.listarAssociados();
        return ResponseEntity.ok(associados);
    }

}