package com.paulomarchon.testepratico.lotacao;

import java.util.List;
import java.util.Optional;

public interface LotacaoDao {
    Optional<Lotacao> buscarLotacaoPorId(Integer id);
    Optional<Lotacao> buscarLotacaoPorPortaria(String portaria);
    List<Lotacao> buscarTodasLotacoes();
    boolean verificaExistenciaDeLotacaoPorId(Integer id);
    boolean verificaExistenciaDeLotacaoPorPortaria(String portaria);
    void cadastrarLotacao(Lotacao lotacao);
    void atualizarLotacao(Lotacao lotacao);
    void excluirLotacao(Integer id);
    Lotacao buscarPorReferenciaDeId(Integer id);
}
