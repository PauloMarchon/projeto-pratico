package com.paulomarchon.testepratico.pessoa;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class PessoaJdbcDataAccessService implements PessoaDao{

    private final JdbcTemplate jdbcTemplate;
    private final PessoaRowMapper pessoaRowMapper;

    public PessoaJdbcDataAccessService(JdbcTemplate jdbcTemplate, PessoaRowMapper pessoaRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.pessoaRowMapper = pessoaRowMapper;
    }

    @Override
    public Optional<Pessoa> buscarPessoaPorNome(String nome) {
        var sql = """
                SELECT pes_id, pes_nome, pes_data_nascimento, pes_sexo, pes_mae, pes_pai
                FROM pessoas.pessoa
                WHERE pes_nome = ?
                """;
        return jdbcTemplate.query(sql, pessoaRowMapper, nome)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Pessoa> selecionarPessoaPorId(Integer id) {
        var sql = """
                SELECT pes_id, pes_nome, pes_data_nascimento, pes_sexo, pes_mae, pes_pai
                FROM pessoas.pessoa
                WHERE pes_id = ?
                """;
        return jdbcTemplate.query(sql, pessoaRowMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public void cadastrarPessoa(Pessoa pessoa) {
        var sql = """
                INSERT INTO pessoas.pessoa(pes_nome, pes_data_nascimento, pes_sexo, pes_mae, pes_pai)
                VALUES (?,?,?,?,?)
                """;
        int result = jdbcTemplate.update(
                sql,
                pessoa.getNome(),
                pessoa.getDataNascimento(),
                pessoa.getSexo(),
                pessoa.getNomeMae(),
                pessoa.getNomePai()
        );
        System.out.println("Cadastro pessoa - retorno da operacao: " + result);
    }

    @Override
    public void atualizarPessoa(Pessoa pessoaAtualizada) {
        if (pessoaAtualizada.getNome() != null) {
            String sql = "UPDATE pessoas.pessoa SET pes_nome = ? WHERE pes_id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    pessoaAtualizada.getNome(),
                    pessoaAtualizada.getId());
            System.out.println("Atualizacao nome pessoa - retorno da operacao: " + result);
        }
        if (pessoaAtualizada.getDataNascimento() != null) {
            String sql = "UPDATE pessoas.pessoa SET pes_data_nascimento = ? WHERE pes_id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    pessoaAtualizada.getDataNascimento(),
                    pessoaAtualizada.getId());
            System.out.println("Atualizacao data nascimento pessoa - retorno da operacao: " + result);
        }
        if (pessoaAtualizada.getSexo() != null) {
            String sql = "UPDATE pessoas.pessoa SET pes_sexo = ? WHERE pes_id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    pessoaAtualizada.getSexo(),
                    pessoaAtualizada.getId());
            System.out.println("Atualizacao sexo pessoa - retorno da operacao: " + result);
        }
        if (pessoaAtualizada.getNomeMae() != null) {
            String sql = "UPDATE pessoas.pessoa SET pes_mae = ? WHERE pes_id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    pessoaAtualizada.getNomeMae(),
                    pessoaAtualizada.getId());
            System.out.println("Atualizacao mae pessoa - retorno da operacao: " + result);
        }
        if (pessoaAtualizada.getNomePai() != null) {
            String sql = "UPDATE pessoas.pessoa SET pes_pai = ? WHERE pes_id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    pessoaAtualizada.getNomePai(),
                    pessoaAtualizada.getId());
            System.out.println("Atualizacao pai pessoa - retorno da operacao: " + result);
        }
    }

    @Override
    public void deletarPessoa(Integer pessoaId) {
        var sql = """
                DELETE
                FROM pessoas.pessoa
                WHERE pes_id = ?
                """;
        int result = jdbcTemplate.update(sql, pessoaId);
        System.out.println("Exclusao pessoa - retorno da operacao: " + result);

    }

    @Override
    public List<Pessoa> buscarTodasPessoas() {
        var sql = """
                SELECT pes_id, pes_nome, pes_data_nascimento, pes_sexo, pes_mae, pes_pai
                FROM pessoas.pessoa
                LIMIT 100
                """;

        return jdbcTemplate.query(sql, pessoaRowMapper);
    }

    @Override
    public boolean verificaExistenciaDePessoaPorId(Integer pessoaId) {
        var sql = """
                SELECT count(pes_id)
                FROM pessoas.pessoa
                WHERE pes_id = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, pessoaId);
        return count != null && count > 0;
    }
}
