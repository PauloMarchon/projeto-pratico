package com.paulomarchon.testepratico.lotacao.dto;

import com.paulomarchon.testepratico.pessoa.Pessoa;
import com.paulomarchon.testepratico.unidade.Unidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record LotacaoCadastro(
        Pessoa pessoa,
        Unidade unidade,
        LocalDate dataLocacao,
        @NotBlank @Size(min = 4) String portaria
) {
}
