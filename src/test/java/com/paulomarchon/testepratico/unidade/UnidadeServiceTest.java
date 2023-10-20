package com.paulomarchon.testepratico.unidade;

import com.paulomarchon.testepratico.exception.FalhaDeAtualizacaoException;
import com.paulomarchon.testepratico.exception.UnidadeJaCadastradaException;
import com.paulomarchon.testepratico.unidade.dto.UnidadeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class UnidadeServiceTest {
    @Mock
    private UnidadeDao unidadeDao;
    private UnidadeService emTeste;
    private final UnidadeDtoMapper unidadeDtoMapper = new UnidadeDtoMapper();

    @BeforeEach
    void setUp() {
        emTeste = new UnidadeService(unidadeDao, unidadeDtoMapper);
    }

    @Test
    void buscarTodasUnidades() {
        //When
        emTeste.buscarTodasUnidades();
        //Then
        verify(unidadeDao).buscarTodasUnidades();
    }

    @Test
    void buscandoUnidadePorNomeComSucesso() {
        //Given
        String nome = "UNIDADE DE TESTE";
        Unidade unidade = new Unidade("UNIDADE DE TESTE", "UT");
        when(unidadeDao.selecionarUnidadePorNome(nome)).thenReturn(Optional.of(unidade));

        UnidadeDto esperado = unidadeDtoMapper.apply(unidade);

        //When
        UnidadeDto atual = emTeste.buscarUnidadePorNome(nome);
        //Then
        assertThat(atual).isEqualTo(esperado);
    }

    @Test
    void buscaDeUnidadePorNomeRetornaOptionalVazio() {
        //Given
        String nome = "UNIDADE DE TESTE";
        //When
        when(unidadeDao.selecionarUnidadePorNome(nome)).thenReturn(Optional.empty());
        //Then
        assertThatThrownBy(() -> emTeste.buscarUnidadePorNome(nome)).isInstanceOf(RuntimeException.class).hasMessage("");
    }

    @Test
    void cadastrarUnidade() {
        //Given
        String nomeUnidade = "UNIDADE DE CADASTRAMENTO";
        String siglaUnidade = "UC";

        when(unidadeDao.existsUnidadeByNome(nomeUnidade)).thenReturn(false);

        UnidadeDto unidadeDto = new UnidadeDto(nomeUnidade, siglaUnidade);

        //When
        emTeste.cadastrarUnidade(unidadeDto);

        //Then
        ArgumentCaptor<Unidade> unidadeArgumentCaptor = ArgumentCaptor.forClass(Unidade.class);

        verify(unidadeDao).cadastrarUnidade(unidadeArgumentCaptor.capture());

        Unidade unidadeCapturada = unidadeArgumentCaptor.getValue();

        assertThat(unidadeCapturada.getId()).isNull();
        assertThat(unidadeCapturada.getNome()).isEqualTo(unidadeDto.nome());
        assertThat(unidadeCapturada.getSigla()).isEqualTo(unidadeDto.sigla());
    }

    @Test
    void cadastrarUnidadeComUmNomeJaRegistrado() {
        //Given
        String nomeUnidade = "UNIDADE DE TRATAMENTO";

        when(unidadeDao.existsUnidadeByNome(nomeUnidade)).thenReturn(true);

        UnidadeDto unidadeDto = new UnidadeDto(nomeUnidade, "UT");

        //When
        assertThatThrownBy(() -> emTeste.cadastrarUnidade(unidadeDto)).isInstanceOf(UnidadeJaCadastradaException.class).hasMessage("Unidade ja cadastrada no sistema!");

        //Then
        verify(unidadeDao, never()).cadastrarUnidade(any());
    }

    @Test
    void excluirUnidade() {
        //Given
        int id = 1;

        when(unidadeDao.existsUnidadeById(id)).thenReturn(true);
        //When
        emTeste.excluirUnidade(id);

        //Then
        verify(unidadeDao).excluirUnidade(id);
    }

    @Test
    void atualizarTodosOsDadosDeUmaUnidadeCadastrada() {
        //Given
        int id = 1;
        Unidade unidade = new Unidade("UNIDADE FISCAL", "UF");
        when(unidadeDao.selecionarUnidadePorId(id)).thenReturn(Optional.of(unidade));

        String novoNome = "UNIDADE DE FISCALIZACAO";
        String novaSigla = "UFZ";

        UnidadeDto unidadeAtualizada = new UnidadeDto(novoNome, novaSigla);

        when(unidadeDao.existsUnidadeByNome(novoNome)).thenReturn(false);

        //When
        emTeste.atualizarUnidade(id, unidadeAtualizada);

        //Then
        ArgumentCaptor<Unidade> unidadeArgumentCaptor = ArgumentCaptor.forClass(Unidade.class);

        verify(unidadeDao).atualizarUnidade(unidadeArgumentCaptor.capture());
        Unidade unidadeCapturada = unidadeArgumentCaptor.getValue();

        assertThat(unidadeCapturada.getNome()).isEqualTo(unidadeAtualizada.nome());
        assertThat(unidadeCapturada.getSigla()).isEqualTo(unidadeAtualizada.sigla());
    }

    @Test
    void atualizarSomenteNomeDeUmaUnidadeCadastrada() {
        //Given
        int id = 1;
        Unidade unidade = new Unidade("UNIDADE DE NEGOCIACAO", "UN");
        when(unidadeDao.selecionarUnidadePorId(id)).thenReturn(Optional.of(unidade));

        String novoNome = "UNIDADE DE NEGOCIOS";

        UnidadeDto unidadeAtualizada = new UnidadeDto(novoNome, null);

        when(unidadeDao.existsUnidadeByNome(novoNome)).thenReturn(false);

        //When
        emTeste.atualizarUnidade(id, unidadeAtualizada);

        //Then
        ArgumentCaptor<Unidade> unidadeArgumentCaptor = ArgumentCaptor.forClass(Unidade.class);

        verify(unidadeDao).atualizarUnidade(unidadeArgumentCaptor.capture());
        Unidade unidadeCapturada = unidadeArgumentCaptor.getValue();

        assertThat(unidadeCapturada.getNome()).isEqualTo(unidadeAtualizada.nome());
        assertThat(unidadeCapturada.getSigla()).isEqualTo(unidade.getSigla());
    }

    @Test
    void atualizarSomenteSiglaDeUmaUnidadeCadastrada() {
        //Given
        int id = 1;
        Unidade unidade = new Unidade("UNIDADE DE NEGOCIOS", "UN");
        when(unidadeDao.selecionarUnidadePorId(id)).thenReturn(Optional.of(unidade));

        UnidadeDto unidadeAtualizada = new UnidadeDto(null, "UNG");

        //When
        emTeste.atualizarUnidade(id, unidadeAtualizada);

        //Then
        ArgumentCaptor<Unidade> unidadeArgumentCaptor = ArgumentCaptor.forClass(Unidade.class);

        verify(unidadeDao).atualizarUnidade(unidadeArgumentCaptor.capture());
        Unidade unidadeCapturada = unidadeArgumentCaptor.getValue();

        assertThat(unidadeCapturada.getNome()).isEqualTo(unidade.getNome());
        assertThat(unidadeCapturada.getSigla()).isEqualTo(unidadeAtualizada.sigla());
    }

    @Test
    @DisplayName("Tentativa de atualizar o nome de uma unidade porem o nome ja e cadastro")
    void atualizarNomeDeUmaUnidadeComUmNomeJaCadastrado() {
        //Given
        int id = 1;
        Unidade unidade = new Unidade("UNIDADE DE PESQUISA", "UP");
        when(unidadeDao.selecionarUnidadePorId(id)).thenReturn(Optional.of(unidade));

        String novoNome = "UNIDADE DE PESQUISAS E ESTUDO";

        UnidadeDto unidadeAtualizada = new UnidadeDto(novoNome, null);

        when(unidadeDao.existsUnidadeByNome(novoNome)).thenReturn(true);

        //When
        assertThatThrownBy(() -> emTeste.atualizarUnidade(id, unidadeAtualizada)).isInstanceOf(UnidadeJaCadastradaException.class).hasMessage("Unidade ja cadastrada no sistema!");

        //Then
        verify(unidadeDao, never()).atualizarUnidade(any());
    }

    @Test
    void atualizacaoDeUnidadeSemAlteracaoDosDados() {
        //Given
        int id = 1;
        Unidade unidade = new Unidade("UNIDADE DE PLANEJAMENTO", "UP");
        when(unidadeDao.selecionarUnidadePorId(id)).thenReturn(Optional.of(unidade));

        UnidadeDto unidadeAtualizada = new UnidadeDto(unidade.getNome(), unidade.getSigla());

        //When
        assertThatThrownBy(() -> emTeste.atualizarUnidade(id, unidadeAtualizada)).isInstanceOf(FalhaDeAtualizacaoException.class).hasMessage("Sem alteracoes nos dados informados");

        //Then
        verify(unidadeDao, never()).atualizarUnidade(any());
    }
}
