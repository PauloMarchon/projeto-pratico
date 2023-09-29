package com.paulomarchon.testepratico.cidade;

import com.paulomarchon.testepratico.cidade.dto.CidadeDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CidadeDtoMapper implements Function<Cidade, CidadeDto> {
    @Override
    public CidadeDto apply(Cidade cidade) {
        return new CidadeDto(
                cidade.getNome(),
                cidade.getUf()
        );
    }
}
