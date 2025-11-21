package com.fontineleantunes.apirestful.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<ErrorResponse> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(false, e.getMessage(), null));
    }

    // Handler para CPF já utilizado — retorna ErrorResponse com status 400
    @ExceptionHandler(CpfJaUtilizadoException.class)
    public ResponseEntity<ErrorResponse> handleCpfJaUtilizado(CpfJaUtilizadoException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(false, e.getMessage(), null));
    }
}
