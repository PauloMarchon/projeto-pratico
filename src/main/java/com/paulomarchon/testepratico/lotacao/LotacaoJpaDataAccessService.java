package com.paulomarchon.testepratico.lotacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("lotacao-jpa")
public class LotacaoJpaDataAccessService implements LotacaoDao{

    private final LotacaoRepository lotacaoRepository;

    public LotacaoJpaDataAccessService(LotacaoRepository lotacaoRepository) {
        this.lotacaoRepository = lotacaoRepository;
    }

    @Override
    public Optional<Lotacao> buscarLotacaoPorId(Integer id) {
        return lotacaoRepository.findById(id);
    }

    @Override
    public Optional<Lotacao> buscarLotacaoPorPortaria(String portaria) {
        return lotacaoRepository.findByPortaria(portaria);
    }

    @Override
    public List<Lotacao> buscarTodasLotacoes() {
        Page<Lotacao> page = lotacaoRepository.findAll(Pageable.ofSize(20));
        return page.getContent();
    }

    @Override
    public boolean verificaExistenciaDeLotacaoPorId(Integer id) {
        return lotacaoRepository.existsById(id);
    }

    @Override
    public boolean verificaExistenciaDeLotacaoPorPortaria(String portaria) {
        return lotacaoRepository.existsByPortaria(portaria);
    }

    @Override
    public void cadastrarLotacao(Lotacao lotacao) {
        lotacaoRepository.save(lotacao);
    }

    @Override
    public void atualizarLotacao(Lotacao lotacao) {
        lotacaoRepository.save(lotacao);
    }

    @Override
    public void excluirLotacao(Integer id) {
        lotacaoRepository.deleteById(id);
    }

    @Override
    public Lotacao buscarPorReferenciaDeId(Integer id) {
        return lotacaoRepository.getReferenceById(id);
    }
}
