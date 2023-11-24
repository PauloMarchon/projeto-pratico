package com.paulomarchon.testepratico.pessoa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulomarchon.testepratico.exception.RecursoNaoEncontradoException;
import com.paulomarchon.testepratico.pessoa.dto.PessoaDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@WebMvcTest(PessoaController.class)
public class PessoaControllerTest {
    @MockBean
    private PessoaService pessoaService;
    @MockBean
    private PessoaDtoMapper pessoaDtoMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void deveBuscarTodasAsPessoas() throws Exception{

        List<PessoaDto> pessoas = new ArrayList<>();
        Arrays.asList(
                new PessoaDto("JULIA", LocalDate.of(1990, 5, 21), "FEMININO", "CLAUDIA", "MARIANO"),
                new PessoaDto("CAMILA", LocalDate.of(1999, 9, 30), "FEMININO", "MARIA", "JOSE"),
                new PessoaDto("JOAO", LocalDate.of(1980, 1, 2), "MASCULINO", "ANA", "MARCIO")
        );

        when(pessoaService.buscarTodasPessoas()).thenReturn(pessoas);

        mockMvc.perform(get("/api/v1/pessoa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(pessoas.size()))
                .andDo(print());
    }

    @Test
    void deveBuscarPessoasPorNome() throws Exception{
        String nomePessoa = "MARIA";
        PessoaDto pessoaDto = new PessoaDto(nomePessoa, LocalDate.of(1990, 5, 21), "FEMININO", "CLAUDIA", "MARIANO");

        when(pessoaService.buscarPessoaPorNome(nomePessoa)).thenReturn(pessoaDto);

        mockMvc.perform(get("/api/v1/pessoa/{nomePessoa}", nomePessoa))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(nomePessoa))
                .andExpect(jsonPath("$.dataNascimento").value(pessoaDto.dataNascimento().toString()))
                .andExpect(jsonPath("$.sexo").value(pessoaDto.sexo()))
                .andExpect(jsonPath("$.nomeMae").value(pessoaDto.nomeMae()))
                .andExpect(jsonPath("$.nomePai").value(pessoaDto.nomePai()))
                .andDo(print());
    }

    @Test
    void deveRetornarPessoaNaoEncontradaPorNome() throws Exception{
        String nomePessoa = "MARIA";

        when(pessoaService.buscarPessoaPorNome(nomePessoa)).thenThrow(RecursoNaoEncontradoException.class);

        mockMvc.perform(get("/api/v1/pessoa/{nomePessoa}", nomePessoa))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void deveCadastrarPessoa() throws Exception{
        PessoaDto pessoaDto = new PessoaDto("MARIA", LocalDate.of(1990, 5, 21), "FEMININO", "CLAUDIA", "MARIANO");

        doNothing().when(pessoaService).cadastrarPessoa(pessoaDto);

        mockMvc.perform(post("/api/v1/pessoa").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaDto)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void deveDeletarUnidadePorId() throws Exception {
        Integer id = 10;

        doNothing().when(pessoaService).deletarPessoa(id);

        mockMvc.perform(delete("/api/v1/pessoa/{unidadeId}", id))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deveAtualizarPessoa() throws Exception {
        Integer id = 1;
        Pessoa pessoa = new Pessoa(id, "JOAO", LocalDate.of(1980, 1, 2), "MASCULINO", "ANA", "MARCIO");
        PessoaDto pessoaDto = pessoaDtoMapper.apply(pessoa);

        PessoaDto pessoaAtualizada = new PessoaDto("JOANA", LocalDate.of(1980, 1, 2), "FEMININO", "MARIA", "ANTONIO");


        when(pessoaService.selecionarPessoaPorId(id)).thenReturn(pessoaDto);
        doNothing().when(pessoaService).atualizarPessoa(any(Integer.class), any(PessoaDto.class));

        mockMvc.perform(put("/api/v1/pessoa/{pessoaId}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaAtualizada)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(pessoaService, times(1)).atualizarPessoa(id, pessoaAtualizada);
    }
}
