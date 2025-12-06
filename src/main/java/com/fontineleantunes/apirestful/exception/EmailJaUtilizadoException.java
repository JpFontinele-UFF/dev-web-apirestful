package com.fontineleantunes.apirestful.exception;

// Exceção lançada quando um email já está em uso por outro aluno
public class EmailJaUtilizadoException extends RuntimeException {
    public EmailJaUtilizadoException(String message) {
        super(message);
    }
}
