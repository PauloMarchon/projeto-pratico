package com.paulomarchon.testepratico.unidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
public class UnidadeJpaDataAccessService implements UnidadeDao {
    private final UnidadeRepository unidadeRepository;

    public UnidadeJpaDataAccessService(UnidadeRepository unidadeRepository) {
        this.unidadeRepository = unidadeRepository;
    }

    @Override
    public List<Unidade> buscarTodasUnidades() {
        Page<Unidade> page = unidadeRepository.findAll(Pageable.ofSize(10));
        return page.getContent();
    }

    @Override
    public Optional<Unidade> selecionarUnidadePorId(Integer unidadeId) {
        return unidadeRepository.findById(unidadeId);
    }

    @Override
    public Optional<Unidade> selecionarUnidadePorNome(String nome) {
        return unidadeRepository.findByNome(nome);
    }

    @Override
    public Page<Unidade> selecionarUnidadePorSigla(String sigla) {
        return unidadeRepository.findBySigla(sigla, Pageable.ofSize(10));
    }

    @Override
    public void cadastrarUnidade(Unidade unidade) {
        unidadeRepository.save(unidade);
    }

    @Override
    public void atualizarUnidade(Unidade unidadeAtualizada) {
        unidadeRepository.save(unidadeAtualizada);
    }

    @Override
    public void excluirUnidade(Integer unidadeId) {
        unidadeRepository.deleteById(unidadeId);
    }

    @Override
    public boolean existsUnidadeByNome(String nome) {
        return unidadeRepository.existsUnidadeByNome(nome);
    }

    @Override
    public boolean existsUnidadeById(Integer unidadeId) {
        return unidadeRepository.existsById(unidadeId);
    }
}
