package com.paulomarchon.testepratico.pessoa;

import java.util.List;
import java.util.Optional;

public interface PessoaDao {
    Optional<Pessoa> buscarPessoaPorNome(String nome);
    Optional<Pessoa> selecionarPessoaPorId(Integer id);
    void cadastrarPessoa(Pessoa pessoa);
    void atualizarPessoa(Pessoa pessoa);
    void deletarPessoa(Integer pessoaId);
    List<Pessoa> buscarTodasPessoas();
    boolean verificaExistenciaDePessoaPorId(Integer pessoaId);
}
