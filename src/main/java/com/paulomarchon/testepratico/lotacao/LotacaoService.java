package com.paulomarchon.testepratico.lotacao;

import com.paulomarchon.testepratico.exception.RecursoDuplicadoException;
import com.paulomarchon.testepratico.exception.RecursoNaoEncontradoException;
import com.paulomarchon.testepratico.lotacao.dto.LotacaoCadastro;
import com.paulomarchon.testepratico.lotacao.dto.LotacaoDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LotacaoService {
    private final LotacaoDao lotacaoDao;
    private final LotacaoDtoMapper lotacaoDtoMapper;

    public LotacaoService(@Qualifier("lotacao-jpa") LotacaoDao lotacaoDao, LotacaoDtoMapper lotacaoDtoMapper) {
        this.lotacaoDao = lotacaoDao;
        this.lotacaoDtoMapper = lotacaoDtoMapper;
    }

    public List<LotacaoDto> buscarTodasLotacoes() {
        return lotacaoDao.buscarTodasLotacoes()
                .stream()
                .map(lotacaoDtoMapper)
                .collect(Collectors.toList());
    }

    public LotacaoDto buscarLotacaoPorId(Integer lotacaoId) {
        return lotacaoDao.buscarLotacaoPorId(lotacaoId)
                .map(lotacaoDtoMapper)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Lotacao nao encontrada!"));
    }

    public LotacaoDto buscarLotacaoPorPortaria(String portaria) {
        return lotacaoDao.buscarLotacaoPorPortaria(portaria)
                .map(lotacaoDtoMapper)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Lotacao nao encontrada!"));
    }

    public boolean verificarExistenciaDeLotacaoPorId(Integer lotacaoId) {
        return lotacaoDao.verificaExistenciaDeLotacaoPorId(lotacaoId);
    }

    public boolean verificarExistenciaDeLotacaoPorPortaria(String portaria) {
        return lotacaoDao.verificaExistenciaDeLotacaoPorPortaria(portaria);
    }

    public void cadastrarLotacao(LotacaoCadastro lotacaoCadastro) {

        String portaria = lotacaoCadastro.portaria();
        if (lotacaoDao.verificaExistenciaDeLotacaoPorPortaria(portaria)) {
            throw new RecursoDuplicadoException("Portaria Ja Cadastrada");
        }

        Lotacao lotacao = new Lotacao(
             lotacaoCadastro.unidade(),
             lotacaoCadastro.pessoa(),
             lotacaoCadastro.dataLocacao(),
             lotacaoCadastro.portaria()
        );

        lotacaoDao.cadastrarLotacao(lotacao);
    }

    public void excluirLotacao(Integer lotacaoId) {
        if (!lotacaoDao.verificaExistenciaDeLotacaoPorId(lotacaoId))
            throw new RecursoNaoEncontradoException("Lotacao nao encontrada");

        lotacaoDao.excluirLotacao(lotacaoId);
    }

    public void atualizarLotacao(Integer lotacaoId, LotacaoDto lotacaoAtualizada) {
        Lotacao lotacao = lotacaoDao.buscarPorReferenciaDeId(lotacaoId);

        //TODO: Atualizacao dos dados

        lotacaoDao.atualizarLotacao(lotacao);
    }
}
