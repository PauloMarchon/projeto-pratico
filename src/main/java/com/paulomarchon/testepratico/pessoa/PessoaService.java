package com.paulomarchon.testepratico.pessoa;

import com.paulomarchon.testepratico.exception.RecursoNaoEncontradoException;
import com.paulomarchon.testepratico.pessoa.dto.PessoaDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {
    private final PessoaDao pessoaDao;
    private final PessoaDtoMapper pessoaDtoMapper;

    public PessoaService(@Qualifier("jdbc") PessoaDao pessoaDao, PessoaDtoMapper pessoaDtoMapper) {
        this.pessoaDao = pessoaDao;
        this.pessoaDtoMapper = pessoaDtoMapper;
    }

    public List<PessoaDto> buscarTodasPessoas() {
        return pessoaDao.buscarTodasPessoas()
                .stream()
                .map(pessoaDtoMapper)
                .collect(Collectors.toList());
    }

    public  PessoaDto buscarPessoaPorNome(String nome) {
        return pessoaDao.buscarPessoaPorNome(nome)
                .map(pessoaDtoMapper)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Nome nao encontrado"));
    }

    public PessoaDto selecionarPessoaPorId(Integer pessoaId) {
        return pessoaDao.selecionarPessoaPorId(pessoaId)
                .map(pessoaDtoMapper)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pessoa com ID informado nao foi encontrada"));
    }

    public void cadastrarPessoa(PessoaDto pessoaCadastro) {

        Pessoa pessoa = new Pessoa(
                pessoaCadastro.nome(),
                pessoaCadastro.dataNascimento(),
                pessoaCadastro.sexo(),
                pessoaCadastro.nomeMae(),
                pessoaCadastro.nomePai()
        );

        pessoaDao.cadastrarPessoa(pessoa);
    }

    public void deletarPessoa(Integer pessoaId) {
        if (pessoaDao.verificaExistenciaDePessoaPorId(pessoaId)) {
            pessoaDao.deletarPessoa(pessoaId);
        } else {
            throw new RecursoNaoEncontradoException("Pessoa com ID informado nao encontrada!");
        }
    }



    public void atualizarPessoa(Integer pessoaId, PessoaDto pessoaAtualizada) {
        Pessoa pessoa = pessoaDao.selecionarPessoaPorId(pessoaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pessoa nao encontrada"));

        boolean alteracaoRealizada = false;

        if(pessoaAtualizada.nome() != null && !pessoaAtualizada.nome().equals(pessoa.getNome())) {
            pessoa.setNome(pessoaAtualizada.nome());
            alteracaoRealizada = true;
        }

        if(pessoaAtualizada.dataNascimento() != null && !pessoaAtualizada.dataNascimento().equals(pessoa.getDataNascimento())) {
            pessoa.setDataNascimento(pessoaAtualizada.dataNascimento());
            alteracaoRealizada = true;
        }

        if(pessoaAtualizada.sexo() != null && !pessoaAtualizada.sexo().equals(pessoa.getSexo())) {
            pessoa.setSexo(pessoaAtualizada.sexo());
            alteracaoRealizada = true;
        }

        if(pessoaAtualizada.nomeMae() != null && !pessoaAtualizada.nomeMae().equals(pessoa.getNomeMae())) {
            pessoa.setNomeMae(pessoaAtualizada.nomeMae());
            alteracaoRealizada = true;
        }

        if(pessoaAtualizada.nomePai() != null && !pessoaAtualizada.nomePai().equals(pessoa.getNomePai())) {
            pessoa.setNomePai(pessoaAtualizada.nomePai());
            alteracaoRealizada = true;
        }

        if(!alteracaoRealizada) {
            throw new RuntimeException("Nenhuma alteracao feita!");
        }

        pessoaDao.atualizarPessoa(pessoa);
    }
}

