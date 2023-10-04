package com.paulomarchon.testepratico.unidade;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UnidadeDao {
    List<Unidade> buscarTodasUnidades();
    Optional<Unidade> selecionarUnidadePorId(Integer unidadeId);
    Optional<Unidade> selecionarUnidadePorNome(String nome);
    Page<Unidade> selecionarUnidadePorSigla(String sigla);
    void cadastrarUnidade(Unidade unidade);
    void atualizarUnidade(Unidade unidadeAtualizada);
    void excluirUnidade(Integer unidadeId);
    boolean existsUnidadeByNome(String nome);
    boolean existsUnidadeById(Integer id);

}
