package com.paulomarchon.testepratico.lotacao.dto;

import com.paulomarchon.testepratico.pessoa.Pessoa;
import com.paulomarchon.testepratico.unidade.Unidade;
import com.paulomarchon.testepratico.unidade.dto.UnidadeDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public record LotacaoDto(
    Pessoa pessoa,
    Unidade unidade,
    LocalDate dataLocacao,
    LocalDate dataRemocao,
    String portaria
) {
}
