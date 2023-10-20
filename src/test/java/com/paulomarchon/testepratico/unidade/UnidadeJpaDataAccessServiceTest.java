package com.paulomarchon.testepratico.unidade;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
public class UnidadeJpaDataAccessServiceTest {
    private UnidadeJpaDataAccessService emTeste;
    private AutoCloseable autoCloseable;
    @Mock
    private UnidadeRepository unidadeRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        emTeste = new UnidadeJpaDataAccessService(unidadeRepository);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void buscarTodasUnidades() {
        Page<Unidade> page = mock(Page.class);
        List<Unidade> unidades = List.of(new Unidade());
        when(page.getContent()).thenReturn(unidades);
        when(unidadeRepository.findAll(any(Pageable.class))).thenReturn(page);

        //When
        List<Unidade> esperado = emTeste.buscarTodasUnidades();

        //Then
        assertThat(esperado).isEqualTo(unidades);
        ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(unidadeRepository).findAll(pageableArgumentCaptor.capture());
        assertThat(pageableArgumentCaptor.getValue()).isEqualTo(Pageable.ofSize(10));
    }

    @Test
    void selecionarUnidadePorId() {
        //Given
        int id = 1;
        //When
        emTeste.selecionarUnidadePorId(id);
        //Then
        verify(unidadeRepository).findById(id);
    }

    @Test
    void cadastrarUnidade() {
        //Given
        Unidade unidade = new Unidade("UNIDADE DE DISTRIBUICAO", "UD");
        //When
        emTeste.cadastrarUnidade(unidade);
        //Then
        verify(unidadeRepository).save(unidade);
    }

    @Test
    void atualizarUnidade() {
        //Given
        Unidade unidade = new Unidade("UNIDADE DE COTACAO", "UC");
        //When
        emTeste.atualizarUnidade(unidade);
        //Then
        verify(unidadeRepository).save(unidade);
    }

    @Test
    void unidadeComNomeJaCadastrado() {
        //Given
        String nomeUnidade = "UNIDADE DE DISTRIBUICAO";
        //When
        emTeste.existsUnidadeByNome(nomeUnidade);
        //Then
        verify(unidadeRepository).existsUnidadeByNome(nomeUnidade);
    }

    @Test
    void deletarUnidadePorId() {
        //Given
        int id = 1;
        //When
        emTeste.excluirUnidade(id);
        //Then
        verify(unidadeRepository).deleteById(id);
    }
}
