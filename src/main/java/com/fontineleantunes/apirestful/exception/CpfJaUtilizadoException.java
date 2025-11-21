package com.fontineleantunes.apirestful.exception;

// Exceção lançada quando um CPF já está em uso por outro aluno
public class CpfJaUtilizadoException extends RuntimeException {
    public CpfJaUtilizadoException(String message) {
        super(message);
    }
}

