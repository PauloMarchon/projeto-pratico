package com.paulomarchon.testepratico.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UnidadeJaCadastradaException extends RuntimeException{
    public UnidadeJaCadastradaException() {
        super("Unidade ja cadastrada no sistema!");
    }
}
