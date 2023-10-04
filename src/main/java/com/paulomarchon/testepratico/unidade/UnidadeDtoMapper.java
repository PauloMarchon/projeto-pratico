package com.paulomarchon.testepratico.unidade;

import com.paulomarchon.testepratico.unidade.dto.UnidadeDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UnidadeDtoMapper implements Function<Unidade, UnidadeDto> {
    @Override
    public UnidadeDto apply(Unidade unidade) {
        return new UnidadeDto(
                unidade.getNome(),
                unidade.getSigla()
        );
    }
}
