package com.paulomarchon.testepratico.pessoa.dto;

import java.time.LocalDate;

public record PessoaDto(
        String nome,
        LocalDate dataNascimento,
        String sexo,
        String nomeMae,
        String nomePai
) {
}
