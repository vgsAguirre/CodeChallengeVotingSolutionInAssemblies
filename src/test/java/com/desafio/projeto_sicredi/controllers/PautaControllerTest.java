package com.desafio.projeto_sicredi.controllers;

import com.desafio.projeto_sicredi.dtos.PautaDto;
import com.desafio.projeto_sicredi.repositories.PautaRepository;
import com.desafio.projeto_sicredi.services.impl.PautaServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitConfig
@WebMvcTest(PautaController.class)
@ContextConfiguration(classes = {PautaController.class})
class PautaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PautaServiceImpl pautaService;

    @MockBean
    private PautaRepository pautaRepository;

    @Test
    @DisplayName("Teste cadastrar pauta")
    void testCadastrarPauta() throws Exception {
        String titulo = "Titulo da pauta";
        String descricao = "Descrição da pauta";
        Integer tempoSessao = 1;

       doNothing().when(pautaService).cadastrarPauta(titulo, descricao, tempoSessao);

        mockMvc.perform(post("/pautas/cadastrar")
                        .param("titulo", titulo)
                        .param("descricao", descricao)
                        .param("tempoSessao", String.valueOf(tempoSessao))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    void testListarPautas() throws Exception {
        PautaDto pauta1 = PautaDto
                .builder()
                .titulo("Titulo da pauta 1")
                .descricao("descricao da pauta 1")
                .build();
        PautaDto pauta2 = PautaDto
                .builder()
                .titulo("Titulo da pauta 2")
                .descricao("descricao da pauta 2")
                .build();
        List<PautaDto> pautas = Arrays.asList(pauta1, pauta2);

        when(pautaService.listarPautas()).thenReturn(pautas);

        mockMvc.perform(get("/pautas/listar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].titulo").value("Titulo da pauta 1"))
                .andExpect(jsonPath("$[0].descricao").value("descricao da pauta 1"))
                .andExpect(jsonPath("$[1].titulo").value("Titulo da pauta 2"))
                .andExpect(jsonPath("$[1].descricao").value("descricao da pauta 2"));
    }
}
