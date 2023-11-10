package com.paulomarchon.testepratico.unidade;

import com.paulomarchon.testepratico.exception.RecursoNaoEncontradoException;
import com.paulomarchon.testepratico.exception.FalhaDeAtualizacaoException;
import com.paulomarchon.testepratico.exception.UnidadeJaCadastradaException;
import com.paulomarchon.testepratico.unidade.dto.UnidadeDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnidadeService {
    private final UnidadeDao unidadeDao;
    private final UnidadeDtoMapper unidadeDtoMapper;

    public UnidadeService(@Qualifier("jpa") UnidadeDao unidadeDao, UnidadeDtoMapper unidadeDtoMapper) {
        this.unidadeDao = unidadeDao;
        this.unidadeDtoMapper = unidadeDtoMapper;
    }

    public List<UnidadeDto> buscarTodasUnidades() {
        return unidadeDao.buscarTodasUnidades()
                .stream()
                .map(unidadeDtoMapper)
                .collect(Collectors.toList());
    }

    public UnidadeDto buscarUnidadePorId(Integer unidadeId) {
        return unidadeDao.selecionarUnidadePorId(unidadeId)
                .map(unidadeDtoMapper)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Unidade com ID informado nao encontrada"));
    }

    public List<UnidadeDto> buscarUnidadePorSigla(String sigla) {
        return unidadeDao.selecionarUnidadePorSigla(sigla)
                .stream()
                .map(unidadeDtoMapper)
                .collect(Collectors.toList());
    }

    public UnidadeDto buscarUnidadePorNome(String nome) {
        return unidadeDao.selecionarUnidadePorNome(nome)
                .map(unidadeDtoMapper)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Unidade com nome informado nao encontrada"));
    }

    public void cadastrarUnidade(UnidadeDto unidadeCadastro) {

        String unidadeNome = unidadeCadastro.nome();
        if (unidadeDao.existsUnidadeByNome(unidadeNome))
            throw new UnidadeJaCadastradaException();

        Unidade unidade = new Unidade(
                unidadeCadastro.nome(),
                unidadeCadastro.sigla()
        );

        unidadeDao.cadastrarUnidade(unidade);
    }

    public void atualizarUnidade(Integer unidadeId, UnidadeDto unidadeAtualizada) {
        Unidade unidade = unidadeDao.selecionarUnidadePorId(unidadeId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Unidade de ID [%s] nao encontrada".formatted(unidadeId)));

        boolean atualizado = false;

        if(unidadeAtualizada.nome() != null && !unidadeAtualizada.nome().equals(unidade.getNome())) {
            if (unidadeDao.existsUnidadeByNome(unidadeAtualizada.nome())) {
                throw new UnidadeJaCadastradaException();
            }
            unidade.setNome(unidadeAtualizada.nome());
            atualizado = true;
        }

        if (unidadeAtualizada.sigla() != null && !unidadeAtualizada.sigla().equals(unidade.getSigla())) {
            unidade.setSigla(unidadeAtualizada.sigla());
            atualizado = true;
        }

        if (!atualizado) {
            throw new FalhaDeAtualizacaoException("Sem alteracoes nos dados informados");
        }

        unidadeDao.atualizarUnidade(unidade);
    }

    public void excluirUnidade(Integer unidadeId) {
        if (!unidadeDao.existsUnidadeById(unidadeId)) {
            throw new RecursoNaoEncontradoException("Unidade de ID [%s] nao encontrada".formatted(unidadeId));
        }
        unidadeDao.excluirUnidade(unidadeId);
    }
}
