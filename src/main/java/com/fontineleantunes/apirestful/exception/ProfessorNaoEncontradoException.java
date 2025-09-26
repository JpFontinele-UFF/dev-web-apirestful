package com.fontineleantunes.apirestful.exception;

public class ProfessorNaoEncontradoException extends RuntimeException {
    public ProfessorNaoEncontradoException(Long id) {
        super("Professor com ID " + id + " não foi encontrado.");
    }
}