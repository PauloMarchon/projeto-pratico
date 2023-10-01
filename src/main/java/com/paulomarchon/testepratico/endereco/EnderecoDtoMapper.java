package com.paulomarchon.testepratico.endereco;

import com.paulomarchon.testepratico.endereco.dto.EnderecoDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class EnderecoDtoMapper implements Function<Endereco, EnderecoDto> {
    @Override
    public EnderecoDto apply(Endereco endereco) {
        return new EnderecoDto(
                endereco.getTipoLogradouro(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade().toString()
        );
    }
}
