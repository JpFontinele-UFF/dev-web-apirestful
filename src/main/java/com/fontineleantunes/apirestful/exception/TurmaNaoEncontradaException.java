package com.fontineleantunes.apirestful.exception;

public class TurmaNaoEncontradaException extends RuntimeException {
    public TurmaNaoEncontradaException(Long id) {
        super("Turma com ID " + id + " não foi encontrada.");
    }
}