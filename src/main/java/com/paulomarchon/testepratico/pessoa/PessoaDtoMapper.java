package com.paulomarchon.testepratico.pessoa;

import com.paulomarchon.testepratico.pessoa.dto.PessoaDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PessoaDtoMapper implements Function<Pessoa, PessoaDto> {
    @Override
    public PessoaDto apply(Pessoa pessoa) {
        return new PessoaDto(
                pessoa.getNome(),
                pessoa.getDataNascimento(),
                pessoa.getSexo(),
                pessoa.getNomeMae(),
                pessoa.getNomePai()
        );
    }
}
