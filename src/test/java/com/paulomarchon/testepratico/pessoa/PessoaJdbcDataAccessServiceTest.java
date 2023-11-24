package com.paulomarchon.testepratico.pessoa;

import com.paulomarchon.testepratico.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class PessoaJdbcDataAccessServiceTest extends AbstractTestcontainers {
    private PessoaJdbcDataAccessService emTeste;
    private final PessoaRowMapper pessoaRowMapper = new PessoaRowMapper();

    @BeforeEach
    void setUp() {
        emTeste = new PessoaJdbcDataAccessService(
                getJdbcTemplate(),
                pessoaRowMapper
        );
    }

    @Test
    void deveBuscarTodasPessoas() {
        //Given
        Pessoa pessoa = new Pessoa("JULIANA", LocalDate.of(1960,5,6), "FEMININO", "CARMEM", "BERLINDO");

        emTeste.cadastrarPessoa(pessoa);

        //When
        List<Pessoa> atual = emTeste.buscarTodasPessoas();

        //Then
        assertThat(atual).isNotEmpty();
    }

    @Test
    void deveSelecionarPessoaPorId() {
        //Given
        String nome = "JULIANA";
        Pessoa pessoa = new Pessoa(nome, LocalDate.of(1960,5,6), "FEMININO", "CARMEM", "BERLINDO");

        emTeste.cadastrarPessoa(pessoa);

        Integer id = emTeste.buscarTodasPessoas()
                .stream()
                .filter(c -> c.getNome().equals(nome))
                .map(Pessoa::getId)
                .findFirst()
                .orElseThrow();

        //When
        Optional<Pessoa> atual = emTeste.selecionarPessoaPorId(id);

        //Then
        assertThat(atual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getNome()).isEqualTo(pessoa.getNome());
            assertThat(c.getDataNascimento()).isEqualTo(pessoa.getDataNascimento());
            assertThat(c.getSexo()).isEqualTo(pessoa.getSexo());
            assertThat(c.getNomeMae()).isEqualTo(pessoa.getNomeMae());
            assertThat(c.getNomePai()).isEqualTo(pessoa.getNomePai());
        });
    }

    @Test
    void deveRetornarVazioQuandoSelecionadaUmaPessoaPorId() {
        //Given
        Integer id = 1;

        //When
        var atual = emTeste.selecionarPessoaPorId(id);

        //Then
        assertThat(atual).isEmpty();
    }

    @Test
    void deveBuscarPessoaPorNome() {
        //Given
        Integer id = 1;
        String nome = "JULIANA";
        Pessoa pessoa = new Pessoa(id, nome, LocalDate.of(1960,5,6), "FEMININO", "CARMEM", "BERLINDO");

        emTeste.cadastrarPessoa(pessoa);

        String nomePessoa = emTeste.buscarTodasPessoas()
                .stream()
                .filter(c -> c.getNome().equals(nome))
                .map(Pessoa::getNome)
                .findFirst()
                .orElseThrow();

        //When
        Optional<Pessoa> atual = emTeste.buscarPessoaPorNome(nomePessoa);

        //Then
        assertThat(atual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(pessoa.getId());
            assertThat(c.getNome()).isEqualTo(nomePessoa);
            assertThat(c.getDataNascimento()).isEqualTo(pessoa.getDataNascimento());
            assertThat(c.getSexo()).isEqualTo(pessoa.getSexo());
            assertThat(c.getNomeMae()).isEqualTo(pessoa.getNomeMae());
            assertThat(c.getNomePai()).isEqualTo(pessoa.getNomePai());
        });
    }

    @Test
    void deveRetornarVazioAoBuscarPessoaPorNome() {
        //Given
        String nome = "JULIANA";

        //When
        var atual = emTeste.buscarPessoaPorNome(nome);

        //Then
        assertThat(atual).isEmpty();
    }

    @Test
    void deveDeletarPessoaPorId() {
        //Given
        String nome = "JULIA";
        Pessoa pessoa = new Pessoa(nome, LocalDate.of(1960,5,6), "FEMININO", "CARMEM", "BERLINDO");

        emTeste.cadastrarPessoa(pessoa);

        Integer id = emTeste.buscarTodasPessoas()
                .stream()
                .filter(c -> c.getNome().equals(nome))
                .map(Pessoa::getId)
                .findFirst()
                .orElseThrow();

        //When
        emTeste.deletarPessoa(id);

        //Then
        Optional<Pessoa> atual = emTeste.selecionarPessoaPorId(id);
        assertThat(atual).isNotPresent();
    }

    @Test
    void deveAtualizarNomeDaPessoa() {
        //Given
        String nome = "JULIA";
        Pessoa pessoa = new Pessoa(nome, LocalDate.of(1960,5,6), "FEMININO", "CARMEM", "BERLINDO");

        emTeste.cadastrarPessoa(pessoa);

        Integer id = emTeste.buscarTodasPessoas()
                .stream()
                .filter(c -> c.getNome().equals(nome))
                .map(Pessoa::getId)
                .findFirst()
                .orElseThrow();

        var novoNome = "JULIANA";

        //When
        Pessoa pessoaAtualizada = new Pessoa();
        pessoaAtualizada.setId(id);
        pessoaAtualizada.setNome(novoNome);

        emTeste.atualizarPessoa(pessoaAtualizada);

        //Then
        Optional<Pessoa> atual = emTeste.selecionarPessoaPorId(id);

        assertThat(atual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getNome()).isEqualTo(novoNome); //ALTERACAO
            assertThat(c.getDataNascimento()).isEqualTo(pessoa.getDataNascimento());
            assertThat(c.getSexo()).isEqualTo(pessoa.getSexo());
            assertThat(c.getNomeMae()).isEqualTo(pessoa.getNomeMae());
            assertThat(c.getNomePai()).isEqualTo(pessoa.getNomePai());
        });
    }

    @Test
    void deveAtualizarDataDeNascimentoDaPessoa() {
        //Given
        String nome = "JULIA";
        Pessoa pessoa = new Pessoa(nome, LocalDate.of(1960,5,6), "FEMININO", "CARMEM", "BERLINDO");

        emTeste.cadastrarPessoa(pessoa);

        Integer id = emTeste.buscarTodasPessoas()
                .stream()
                .filter(c -> c.getNome().equals(nome))
                .map(Pessoa::getId)
                .findFirst()
                .orElseThrow();

        LocalDate novaData = LocalDate.of(1990,6,12);

        //When
        Pessoa pessoaAtualizada = new Pessoa();
        pessoaAtualizada.setId(id);
        pessoaAtualizada.setDataNascimento(novaData);

        emTeste.atualizarPessoa(pessoaAtualizada);

        //Then
        Optional<Pessoa> atual = emTeste.selecionarPessoaPorId(id);

        assertThat(atual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getNome()).isEqualTo(pessoa.getNome());
            assertThat(c.getDataNascimento()).isEqualTo(novaData); //ALTERACAO
            assertThat(c.getSexo()).isEqualTo(pessoa.getSexo());
            assertThat(c.getNomeMae()).isEqualTo(pessoa.getNomeMae());
            assertThat(c.getNomePai()).isEqualTo(pessoa.getNomePai());
        });
    }

    @Test
    void deveAtualizarTodosOsDadosDaPessoa() {
        //Given
        String nome = "JULIA";
        Pessoa pessoa = new Pessoa(nome, LocalDate.of(1960,5,6), "FEMININO", "CARMEM", "BERLINDO");

        emTeste.cadastrarPessoa(pessoa);

        Integer id = emTeste.buscarTodasPessoas()
                .stream()
                .filter(c -> c.getNome().equals(nome))
                .map(Pessoa::getId)
                .findFirst()
                .orElseThrow();

        //When
        Pessoa pessoaAtualizada = new Pessoa();
        pessoaAtualizada.setId(id);

        String novoNome = "EVANGELINA";
        pessoaAtualizada.setNome(novoNome);

        LocalDate novaData = LocalDate.of(1999,10, 5);
        pessoaAtualizada.setDataNascimento(novaData);

        pessoaAtualizada.setSexo("MASCULINO");
        pessoaAtualizada.setNomeMae("CLARICE");
        pessoaAtualizada.setNomePai("RONALDO");

        emTeste.atualizarPessoa(pessoaAtualizada);

        // Then
        Optional<Pessoa> atual = emTeste.selecionarPessoaPorId(id);

        assertThat(atual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getNome()).isEqualTo(novoNome);
            assertThat(c.getDataNascimento()).isEqualTo(novaData);
            assertThat(c.getSexo()).isEqualTo("MASCULINO");
            assertThat(c.getNomeMae()).isEqualTo("CLARICE");
            assertThat(c.getNomePai()).isEqualTo("RONALDO");
        });
    }

    @Test
    void deveRetornarUmaAtualizacaoDePessoaSemNenhumaModificacao() {
        //Given
        String nome = "JULIA";
        Pessoa pessoa = new Pessoa(nome, LocalDate.of(1960,5,6), "FEMININO", "CARMEM", "BERLINDO");

        emTeste.cadastrarPessoa(pessoa);

        Integer id = emTeste.buscarTodasPessoas()
                .stream()
                .filter(c -> c.getNome().equals(nome))
                .map(Pessoa::getId)
                .findFirst()
                .orElseThrow();

        //When - atualizacao sem modificacao nos dados
        Pessoa pessoaAtualizada = new Pessoa();
        pessoaAtualizada.setId(id);

        emTeste.atualizarPessoa(pessoaAtualizada);

        //Then
        Optional<Pessoa> atual = emTeste.selecionarPessoaPorId(id);

        assertThat(atual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getNome()).isEqualTo(pessoa.getNome());
            assertThat(c.getDataNascimento()).isEqualTo(pessoa.getDataNascimento());
            assertThat(c.getSexo()).isEqualTo(pessoa.getSexo());
            assertThat(c.getNomeMae()).isEqualTo(pessoa.getNomeMae());
            assertThat(c.getNomePai()).isEqualTo(pessoa.getNomePai());
        });
    }

    @Test
    void deveRetornarExistenciaDePessoaPorId() {
        //Given
        String nome = "JULIA";
        Pessoa pessoa = new Pessoa(nome, LocalDate.of(1960,5,6), "FEMININO", "CARMEM", "BERLINDO");

        emTeste.cadastrarPessoa(pessoa);

        Integer id = emTeste.buscarTodasPessoas()
                .stream()
                .filter(c -> c.getNome().equals(nome))
                .map(Pessoa::getId)
                .findFirst()
                .orElseThrow();

        //When
        var atual = emTeste.verificaExistenciaDePessoaPorId(id);

        //Then
        assertThat(atual).isTrue();
    }

    @Test
    void deveRetornarFalsoOuNaoPresenteAoVerificarExistenciaDePessoaPorId() {
        //Given
        Integer id = 10;

        //When
        var atual = emTeste.verificaExistenciaDePessoaPorId(id);

        //Then
        assertThat(atual).isFalse();
    }
}
