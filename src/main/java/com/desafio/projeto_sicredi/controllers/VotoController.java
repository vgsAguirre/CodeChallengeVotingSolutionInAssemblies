package com.desafio.projeto_sicredi.controllers;

import com.desafio.projeto_sicredi.enums.VotoEnum;
import com.desafio.projeto_sicredi.services.impl.VotoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/votos")
@Tag(name = "Votos", description = "API para gerenciar votos")
public class VotoController {

    private final VotoServiceImpl votoService;

    public VotoController(VotoServiceImpl votoService) {
        this.votoService = votoService;
    }

    @PostMapping("/votar")
    @Operation(summary = "Registrar Voto", description = "Endpoint para registrar um novo voto.")
    @ApiResponse(responseCode = "201", description = "Voto registrado com sucesso.")
    @ApiResponse(responseCode = "400", description = "CPF não pode ser vazio ou nulo.")
    @ApiResponse(responseCode = "400", description = "CPF inválido.")
    @ApiResponse(responseCode = "409", description = "Associado já votou nessa pauta.")
    @ApiResponse(responseCode = "404", description = "Não existe uma sessão aberta.")
    @ApiResponse(responseCode = "404", description = "Associado não cadastrado para efetuar a votação.")
    @ApiResponse(responseCode = "500", description = "Erro interno.")
    public ResponseEntity<String> votar(
            @RequestParam VotoEnum voto,
            @RequestParam String cpfAssociado) {
        votoService.registrarVoto(voto, cpfAssociado);
        return ResponseEntity.status(HttpStatus.CREATED).body("Voto registrado com sucesso.");
    }
}