package com.paulomarchon.testepratico.unidade;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulomarchon.testepratico.exception.RecursoNaoEncontradoException;
import com.paulomarchon.testepratico.unidade.dto.UnidadeDto;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UnidadeController.class)
public class UnidadeControllerTest {
    @MockBean
    private UnidadeService unidadeService;
    @MockBean
    private UnidadeDtoMapper unidadeDtoMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void deveRetornarTodasUnidades() throws Exception {

        List<UnidadeDto> unidades = new ArrayList<>();
        Arrays.asList(new UnidadeDto("UNIDADE TESTE1", "UT1"),
                new UnidadeDto( "UNIDADE TESTE2", "UT2"),
                new UnidadeDto( "UNIDADE TESTE3", "UT3"));

        when(unidadeService.buscarTodasUnidades()).thenReturn(unidades);

        mockMvc.perform(get("/api/v1/unidade"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(unidades.size()))
                .andDo(print());
    }

    @Test
    void deveBuscarUnidadePorNome() throws Exception {
        String nomeUnidade = "UNIDADE TESTE";
        UnidadeDto unidadeDto = new UnidadeDto(nomeUnidade, "UT");

        when(unidadeService.buscarUnidadePorNome(nomeUnidade)).thenReturn(unidadeDto);

        mockMvc.perform(get("/api/v1/unidade/{nomeUnidade}", nomeUnidade))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(nomeUnidade))
                .andExpect(jsonPath("$.sigla").value(unidadeDto.sigla()))
                .andDo(print());
    }

    @Test
    void deveRetornarUnidadeNaoEncontradaPorNome() throws Exception{
        String nomeUnidade = "NOME BUSCADO";

        UnidadeDto unidadeDto = new UnidadeDto("NOME UNIDADE", "UT");

        when(unidadeService.buscarUnidadePorNome(nomeUnidade)).thenThrow(RecursoNaoEncontradoException.class);

        mockMvc.perform(get("/api/v1/unidade/{nomeUnidade}", nomeUnidade))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void deveCriarUnidade() throws Exception {

        UnidadeDto unidadeDto = new UnidadeDto("UNIDADE TESTE", "UT");

        mockMvc.perform(post("/api/v1/unidade").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unidadeDto)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void deveDeletarUnidadePorId() throws Exception {
        Integer id = 1;

        doNothing().when(unidadeService).excluirUnidade(id);

        mockMvc.perform(delete("/api/v1/unidade/{unidadeId}", id))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deveAtualizarUnidade() throws Exception {
        Integer id = 1;

        Unidade unidade = new Unidade(id, "NOME UNIDADE", "NU");
        UnidadeDto unidadeAtualizada = new UnidadeDto("NOME CORRETO UNIDADE", "NCU");
        UnidadeDto unidadeDto = unidadeDtoMapper.apply(unidade);

        when(unidadeService.buscarUnidadePorId(id)).thenReturn(unidadeDto);
        doNothing().when(unidadeService).atualizarUnidade(any(), any(UnidadeDto.class));

        mockMvc.perform(put("/api/v1/unidade/{unidadeId}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unidadeAtualizada)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
