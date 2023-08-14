package com.desafio.projeto_sicredi.controllers;

import com.desafio.projeto_sicredi.dtos.SessaoDto;
import com.desafio.projeto_sicredi.services.impl.SessaoServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitConfig
@WebMvcTest(SessaoController.class)
@ContextConfiguration(classes = {SessaoController.class})
class SessaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessaoServiceImpl sessaoServiceImpl;

    @Test
    void testListarSessoes() throws Exception {

        List<SessaoDto> mockSessoes = new ArrayList<>();

        when(sessaoServiceImpl.listarSessoes()).thenReturn(mockSessoes);

        mockMvc.perform(get("/sessoes/listar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}