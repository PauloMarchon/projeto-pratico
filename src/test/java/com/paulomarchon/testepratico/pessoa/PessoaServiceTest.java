package com.paulomarchon.testepratico.pessoa;

import com.paulomarchon.testepratico.exception.RecursoNaoEncontradoException;
import com.paulomarchon.testepratico.pessoa.dto.PessoaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class PessoaServiceTest {
    @Mock
    private PessoaDao pessoaDao;
    private PessoaService emTeste;
    private final PessoaDtoMapper pessoaDtoMapper = new PessoaDtoMapper();

    @BeforeEach
    void setUp() {
        emTeste = new PessoaService(pessoaDao, pessoaDtoMapper);
    }

    @Test
    void deveRetornarTodasAsPessoas() {
        //When
        emTeste.buscarTodasPessoas();
        //Then
        verify(pessoaDao).buscarTodasPessoas();
    }
    @Test
    void deveRetornarUmaPessoaPorId() {
        //Given
        Integer id = 5;
        Pessoa pessoa = new Pessoa(id, "MARCIO", LocalDate.of(1990, 5, 5), "MASCULINO","FABIOLA", "ALFREDO");
        when(pessoaDao.selecionarPessoaPorId(id)).thenReturn(Optional.of(pessoa));

        PessoaDto esperado = pessoaDtoMapper.apply(pessoa);

        //When
        PessoaDto atual = emTeste.selecionarPessoaPorId(id);

        //Then
        assertThat(atual).isEqualTo(esperado);
    }
    @Test
    void deveRetornarErroAoTentarBuscarPessoaComIdInexistente() {
        //Given
        Integer id = 10;

        when(pessoaDao.selecionarPessoaPorId(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> emTeste.selecionarPessoaPorId(id)).isInstanceOf(RecursoNaoEncontradoException.class).hasMessage("Pessoa com ID informado nao foi encontrada");
    }

    @Test
    void deveRetornarUmaPessoaPorNome() {
        //Given
        String nomePessoa = "MANOEL";

        Pessoa pessoa = new Pessoa(nomePessoa, LocalDate.of(1999, 7, 7), "MASCULINO", "ROBERTA", "JOSE");
        when(pessoaDao.buscarPessoaPorNome(nomePessoa)).thenReturn(Optional.of(pessoa));

        PessoaDto esperado = pessoaDtoMapper.apply(pessoa);

        //When
        PessoaDto atual = emTeste.buscarPessoaPorNome(nomePessoa);

        //Then
        assertThat(atual).isEqualTo(esperado);
    }

    @Test
    void deveRetornarErroAoBuscarUmaPessoaPorNomeInexistente() {
        //Given
        String nomePessoa = "BRUNA";

        when(pessoaDao.buscarPessoaPorNome(nomePessoa)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> emTeste.buscarPessoaPorNome(nomePessoa)).isInstanceOf(RecursoNaoEncontradoException.class).hasMessage("Nome nao encontrado");
    }

    @Test
    void deveCadastrarUmaPessoa() {
        //Given
        PessoaDto pessoaCadastro = new PessoaDto("MARCELA", LocalDate.of(1990,6,6), "FEMININO", "LARISSA", "FABRICIO");

        //When
        emTeste.cadastrarPessoa(pessoaCadastro);

        //Then
        ArgumentCaptor<Pessoa> pessoaArgumentCaptor = ArgumentCaptor.forClass(Pessoa.class);

        verify(pessoaDao).cadastrarPessoa(pessoaArgumentCaptor.capture());

        Pessoa pessoaCaptor = pessoaArgumentCaptor.getValue();

        assertThat(pessoaCaptor.getId()).isNull();
        assertThat(pessoaCaptor.getNome()).isEqualTo(pessoaCadastro.nome());
        assertThat(pessoaCaptor.getDataNascimento()).isEqualTo(pessoaCadastro.dataNascimento());
        assertThat(pessoaCaptor.getSexo()).isEqualTo(pessoaCadastro.sexo());
        assertThat(pessoaCaptor.getNomeMae()).isEqualTo(pessoaCadastro.nomeMae());
        assertThat(pessoaCaptor.getNomePai()).isEqualTo(pessoaCadastro.nomePai());
    }

    @Test
    void deveDeletarUmaPessoaPorId() {
        //Given
        Integer id = 1;

        when(pessoaDao.verificaExistenciaDePessoaPorId(id)).thenReturn(true);

        //When
        emTeste.deletarPessoa(id);

        //Then
        verify(pessoaDao).deletarPessoa(id);
    }

    @Test
    void deveRetornarErroAoTentarDeletarUmaPessoaComIdInexistente() {
        //Given
        Integer id = 10;

        when(pessoaDao.verificaExistenciaDePessoaPorId(id)).thenReturn(false);

        //When
        assertThatThrownBy(() -> emTeste.deletarPessoa(id)).isInstanceOf(RecursoNaoEncontradoException.class).hasMessage("Pessoa com ID informado nao encontrada!");

        //Then
        verify(pessoaDao, never()).deletarPessoa(id);
    }

    @Test
    void deveAtualizarPessoaAlterandoTodasAsPropriedades() {
        //Given
        Integer id = 10;
        Pessoa pessoa = new Pessoa(id, "JULIANA", LocalDate.of(1960,5,6), "MASCULINO", "CARMEM", "BERLINDO");
        when(pessoaDao.selecionarPessoaPorId(id)).thenReturn(Optional.of(pessoa));

        PessoaDto pessoaAtualizada = new PessoaDto("JULIA", LocalDate.of(1970,6,21), "FEMININO", "CARMERINA", "GERMINO");

        //When
        emTeste.atualizarPessoa(id, pessoaAtualizada);

        //Then
        ArgumentCaptor<Pessoa> pessoaArgumentCaptor = ArgumentCaptor.forClass(Pessoa.class);

        verify(pessoaDao).atualizarPessoa(pessoaArgumentCaptor.capture());
        Pessoa pessoaCaptor = pessoaArgumentCaptor.getValue();

        assertThat(pessoaCaptor.getNome()).isEqualTo(pessoaAtualizada.nome());
        assertThat(pessoaCaptor.getDataNascimento()).isEqualTo(pessoaAtualizada.dataNascimento());
        assertThat(pessoaCaptor.getSexo()).isEqualTo(pessoaAtualizada.sexo());
        assertThat(pessoaCaptor.getNomeMae()).isEqualTo(pessoaAtualizada.nomeMae());
        assertThat(pessoaCaptor.getNomePai()).isEqualTo(pessoaAtualizada.nomePai());
    }

    @Test
    void deveAtualizarPessoaAlterandoApenasSeuNome() {
        //Given
        Integer id = 10;
        Pessoa pessoa = new Pessoa(id, "JULIANA", LocalDate.of(1960,5,6), "MASCULINO", "CARMEM", "BERLINDO");
        when(pessoaDao.selecionarPessoaPorId(id)).thenReturn(Optional.of(pessoa));

        PessoaDto pessoaAtualizada = new PessoaDto("JULIA", null, null, null, null);

        //When
        emTeste.atualizarPessoa(id, pessoaAtualizada);

        //Then
        ArgumentCaptor<Pessoa> pessoaArgumentCaptor = ArgumentCaptor.forClass(Pessoa.class);

        verify(pessoaDao).atualizarPessoa(pessoaArgumentCaptor.capture());
        Pessoa pessoaCaptor = pessoaArgumentCaptor.getValue();

        assertThat(pessoaCaptor.getNome()).isEqualTo(pessoaAtualizada.nome());
        assertThat(pessoaCaptor.getDataNascimento()).isEqualTo(pessoa.getDataNascimento());
        assertThat(pessoaCaptor.getSexo()).isEqualTo(pessoa.getSexo());
        assertThat(pessoaCaptor.getNomeMae()).isEqualTo(pessoa.getNomeMae());
        assertThat(pessoaCaptor.getNomePai()).isEqualTo(pessoa.getNomePai());
    }

    @Test
    void deveAtualizarPessoaAlterandoApenasSuaDataDeNascimento() {
        //Given
        Integer id = 10;
        Pessoa pessoa = new Pessoa(id, "JULIANA", LocalDate.of(1960,5,6), "FEMININO", "CARMEM", "BERLINDO");
        when(pessoaDao.selecionarPessoaPorId(id)).thenReturn(Optional.of(pessoa));

        PessoaDto pessoaAtualizada = new PessoaDto(null, LocalDate.of(2000,8,28), null, null, null);

        //When
        emTeste.atualizarPessoa(id, pessoaAtualizada);

        //Then
        ArgumentCaptor<Pessoa> pessoaArgumentCaptor = ArgumentCaptor.forClass(Pessoa.class);

        verify(pessoaDao).atualizarPessoa(pessoaArgumentCaptor.capture());
        Pessoa pessoaCaptor = pessoaArgumentCaptor.getValue();

        assertThat(pessoaCaptor.getNome()).isEqualTo(pessoa.getNome());
        assertThat(pessoaCaptor.getDataNascimento()).isEqualTo(pessoaAtualizada.dataNascimento());
        assertThat(pessoaCaptor.getSexo()).isEqualTo(pessoa.getSexo());
        assertThat(pessoaCaptor.getNomeMae()).isEqualTo(pessoa.getNomeMae());
        assertThat(pessoaCaptor.getNomePai()).isEqualTo(pessoa.getNomePai());
    }

    @Test
    void deveAtualizarPessoaAlterandoApenasSeuSexo() {
        //Given
        Integer id = 10;
        Pessoa pessoa = new Pessoa(id, "JULIANA", LocalDate.of(1960,5,6), "MASCULINO", "CARMEM", "BERLINDO");
        when(pessoaDao.selecionarPessoaPorId(id)).thenReturn(Optional.of(pessoa));

        PessoaDto pessoaAtualizada = new PessoaDto(null, null, "FEMININO", null, null);

        //When
        emTeste.atualizarPessoa(id, pessoaAtualizada);

        //Then
        ArgumentCaptor<Pessoa> pessoaArgumentCaptor = ArgumentCaptor.forClass(Pessoa.class);

        verify(pessoaDao).atualizarPessoa(pessoaArgumentCaptor.capture());
        Pessoa pessoaCaptor = pessoaArgumentCaptor.getValue();

        assertThat(pessoaCaptor.getNome()).isEqualTo(pessoa.getNome());
        assertThat(pessoaCaptor.getDataNascimento()).isEqualTo(pessoa.getDataNascimento());
        assertThat(pessoaCaptor.getSexo()).isEqualTo(pessoaAtualizada.sexo());
        assertThat(pessoaCaptor.getNomeMae()).isEqualTo(pessoa.getNomeMae());
        assertThat(pessoaCaptor.getNomePai()).isEqualTo(pessoa.getNomePai());
    }

    @Test
    void deveAtualizarPessoaAlterandoApenasNomeDaMae() {
        //Given
        Integer id = 10;
        Pessoa pessoa = new Pessoa(id, "JULIANA", LocalDate.of(1960,5,6), "FEMININO", "CARMEM", "BERLINDO");
        when(pessoaDao.selecionarPessoaPorId(id)).thenReturn(Optional.of(pessoa));

        PessoaDto pessoaAtualizada = new PessoaDto(null, null, null, "CARINA", null);

        //When
        emTeste.atualizarPessoa(id, pessoaAtualizada);

        //Then
        ArgumentCaptor<Pessoa> pessoaArgumentCaptor = ArgumentCaptor.forClass(Pessoa.class);

        verify(pessoaDao).atualizarPessoa(pessoaArgumentCaptor.capture());
        Pessoa pessoaCaptor = pessoaArgumentCaptor.getValue();

        assertThat(pessoaCaptor.getNome()).isEqualTo(pessoa.getNome());
        assertThat(pessoaCaptor.getDataNascimento()).isEqualTo(pessoa.getDataNascimento());
        assertThat(pessoaCaptor.getSexo()).isEqualTo(pessoa.getSexo());
        assertThat(pessoaCaptor.getNomeMae()).isEqualTo(pessoaAtualizada.nomeMae());
        assertThat(pessoaCaptor.getNomePai()).isEqualTo(pessoa.getNomePai());
    }

    @Test
    void deveAtualizarPessoaAlterandoApenasNomeDoPai() {
        //Given
        Integer id = 10;
        Pessoa pessoa = new Pessoa(id, "JULIANA", LocalDate.of(1960,5,6), "FEMININO", "CARMEM", "BERLINDO");
        when(pessoaDao.selecionarPessoaPorId(id)).thenReturn(Optional.of(pessoa));

        PessoaDto pessoaAtualizada = new PessoaDto(null, null, null, null, "GERUNDIOLINO");

        //When
        emTeste.atualizarPessoa(id, pessoaAtualizada);

        //Then
        ArgumentCaptor<Pessoa> pessoaArgumentCaptor = ArgumentCaptor.forClass(Pessoa.class);

        verify(pessoaDao).atualizarPessoa(pessoaArgumentCaptor.capture());
        Pessoa pessoaCaptor = pessoaArgumentCaptor.getValue();

        assertThat(pessoaCaptor.getNome()).isEqualTo(pessoa.getNome());
        assertThat(pessoaCaptor.getDataNascimento()).isEqualTo(pessoa.getDataNascimento());
        assertThat(pessoaCaptor.getSexo()).isEqualTo(pessoa.getSexo());
        assertThat(pessoaCaptor.getNomeMae()).isEqualTo(pessoa.getNomeMae());
        assertThat(pessoaCaptor.getNomePai()).isEqualTo(pessoaAtualizada.nomePai());
    }

    @Test
    void deveRetornarExcecaoAoAtualizarPessoaComNenhumaAlteracao() {
        //Given
        Integer id = 10;
        Pessoa pessoa = new Pessoa(id, "JULIANA", LocalDate.of(1960,5,6), "FEMININO", "CARMEM", "BERLINDO");
        when(pessoaDao.selecionarPessoaPorId(id)).thenReturn(Optional.of(pessoa));

        PessoaDto pessoaAtualizada = new PessoaDto(pessoa.getNome(), pessoa.getDataNascimento(), pessoa.getSexo(), pessoa.getNomeMae(), pessoa.getNomePai());

        //When
        assertThatThrownBy(() -> emTeste.atualizarPessoa(id, pessoaAtualizada)).isInstanceOf(RuntimeException.class).hasMessage("Nenhuma alteracao feita!");

        //Then
        verify(pessoaDao, never()).atualizarPessoa(any());
    }
}
