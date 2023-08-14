package com.desafio.projeto_sicredi.controllers;

import com.desafio.projeto_sicredi.dtos.AssociadoDto;
import com.desafio.projeto_sicredi.repositories.AssociadoRepository;
import com.desafio.projeto_sicredi.services.impl.AssociadoServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitConfig
@AutoConfigureMockMvc
@WebMvcTest(AssociadoController.class)
@ContextConfiguration(classes = {AssociadoController.class})
class AssociadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssociadoServiceImpl associadoService;

    @MockBean
    private AssociadoRepository associadoRepository;

    @Test
    @DisplayName("Test cadastrar associado")
    void testCadastrarAssociado() throws Exception {
        String nome = "João da Silva";
        String cpf = "12345678900";

        when(associadoService.cadastrarAssociado(nome, cpf)).thenReturn(AssociadoDto
                .builder()
                .nome("João da Silva")
                .cpf("12345678900")
                .build());

        mockMvc.perform(post("/associados/cadastrar")
                        .param("nome", nome)
                        .param("cpf", cpf)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Test listar associados")
    void testListarAssociados() throws Exception {
        AssociadoDto associado1 = AssociadoDto
                .builder()
                .nome("João da Silva")
                .cpf("12345678900")
                .build();
        AssociadoDto associado2 = AssociadoDto
                .builder()
                .nome("Maria Souza")
                .cpf("98765432100")
                .build();
        List<AssociadoDto> associados = Arrays.asList(associado1, associado2);

        when(associadoService.listarAssociados()).thenReturn(associados);

        mockMvc.perform(get("/associados/listar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("João da Silva"))
                .andExpect(jsonPath("$[0].cpf").value("12345678900"))
                .andExpect(jsonPath("$[1].nome").value("Maria Souza"))
                .andExpect(jsonPath("$[1].cpf").value("98765432100"));
    }
}
