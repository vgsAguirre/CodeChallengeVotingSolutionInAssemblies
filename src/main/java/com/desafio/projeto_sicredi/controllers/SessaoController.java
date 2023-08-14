package com.desafio.projeto_sicredi.controllers;

import com.desafio.projeto_sicredi.dtos.SessaoDto;
import com.desafio.projeto_sicredi.services.impl.SessaoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sessoes")
@Tag(name = "Sessões", description = "API para gerenciar sessões de votação")
public class SessaoController {
    private final SessaoServiceImpl sessaoService;

    @GetMapping("/listar")
    @Operation(summary = "Listar Sessoes", description = "Endpoint para listar todas as sessoes.")
    @ApiResponse(responseCode = "200", description = "Lista de sessoes retornada com sucesso.")
    @ApiResponse(responseCode = "404", description = "Não há sessões cadastradas.")
    @ApiResponse(responseCode = "500", description = "Erro interno.")
    public ResponseEntity<List<SessaoDto>> listarSessoes() {
        List<SessaoDto> sessoes = sessaoService.listarSessoes();
        return ResponseEntity.ok(sessoes);
    }
}
