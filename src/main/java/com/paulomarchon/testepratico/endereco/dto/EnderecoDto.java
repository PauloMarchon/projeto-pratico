package com.paulomarchon.testepratico.endereco.dto;

public record EnderecoDto(
        String tipoLogradouro,
        String logradouro,
        int numero,
        String bairro,
        String cidade
) {
}
