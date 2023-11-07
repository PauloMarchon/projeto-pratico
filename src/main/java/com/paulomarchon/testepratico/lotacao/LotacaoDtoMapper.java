package com.paulomarchon.testepratico.lotacao;

import com.paulomarchon.testepratico.lotacao.dto.LotacaoDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class LotacaoDtoMapper implements Function<Lotacao, LotacaoDto> {
    @Override
    public LotacaoDto apply(Lotacao lotacao) {
        return new LotacaoDto(
                lotacao.getPessoa(),
                lotacao.getUnidade(),
                lotacao.getDataLotacao(),
                lotacao.getDataRemocao(),
                lotacao.getPortaria()
        );
    }
}
