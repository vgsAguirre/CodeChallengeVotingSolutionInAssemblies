package com.desafio.projeto_sicredi.controllers;

import com.desafio.projeto_sicredi.dtos.PautaDto;
import com.desafio.projeto_sicredi.dtos.ResultadoVotacaoDto;
import com.desafio.projeto_sicredi.services.impl.PautaServiceImpl;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/v1/pautas")
@Tag(name = "Pautas", description = "API para gerenciar pautas")
public class PautaController {

    private final PautaServiceImpl pautaService;

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar Pauta", description = "Endpoint para cadastrar uma nova pauta.")
    @ApiResponse(responseCode = "201", description = "Pauta cadastrada com sucesso.")
    @ApiResponse(responseCode = "400", description = "Requisição inválida.")
    @ApiResponse(responseCode = "404", description = "Não há associados cadastrados.")
    @ApiResponse(responseCode = "409", description = "Já existe uma pauta com a sessão aberta.")
    @ApiResponse(responseCode = "500", description = "Erro interno.")
    public ResponseEntity<String> cadastrarPauta(
            @NotBlank(message = "O título é obrigatório.")
            @RequestParam String titulo,
            @NotBlank(message = "A descrição é obrigatória.")
            @RequestParam String descricao,
            @RequestParam(required = false, defaultValue = "1") Integer tempoSessao) {
        pautaService.cadastrarPauta(titulo, descricao, tempoSessao);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pauta cadastrada com sucesso.");
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar Pautas", description = "Endpoint para listar todas as pautas.")
    @ApiResponse(responseCode = "200", description = "Lista de pautas retornada com sucesso.")
    @ApiResponse(responseCode = "404", description = "Não há pautas cadastradas.")
    @ApiResponse(responseCode = "500", description = "Erro interno.")
    public ResponseEntity<List<PautaDto>> listarPautas() {
        List<PautaDto> pautas = pautaService.listarPautas();
        return ResponseEntity.ok(pautas);
    }

    @RestController
    @Hidden
    @RequestMapping("/resultado-votacao")
    public class ResultadoVotacaoController {
        private final Logger logger = LoggerFactory.getLogger(ResultadoVotacaoController.class);

        @Operation(summary = "Obter resultado da votação")
        @ApiResponse(responseCode = "200", description = "Resultado da votação com sucesso.")
        @ApiResponse(responseCode = "404", description = "Pauta não encontrada.")
        @ApiResponse(responseCode = "500", description = "Erro interno.")
        @GetMapping("/{pautaId}")
        public ResponseEntity<ResultadoVotacaoDto> obterResultadoVotacao(
                @PathVariable Long pautaId) {
            ResultadoVotacaoDto resultadoVotacaoDto = pautaService.obterResultadoVotacao(pautaId);

            logger.info("Total de votos: {}", resultadoVotacaoDto.getTotalVotos());
            logger.info("Votos SIM: {}", resultadoVotacaoDto.getVotosSim());
            logger.info("Votos NÃO: {}", resultadoVotacaoDto.getVotosNao());
            logger.info("Aprovado: {}", resultadoVotacaoDto.getAprovado());

            return ResponseEntity.ok(resultadoVotacaoDto);
        }
    }
}