package com.paulomarchon.testepratico.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class RecursoDuplicadoException extends RuntimeException{
    public RecursoDuplicadoException(String mensagem) { super(mensagem);}
}
