package com.paulomarchon.testepratico.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class FalhaDeAtualizacaoException extends RuntimeException{
    public FalhaDeAtualizacaoException(String mensagem) {
        super(mensagem);
    }
}
