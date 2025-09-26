package com.fontineleantunes.apirestful.exception;

public class InscricaoNaoEncontradaException extends RuntimeException {
    public InscricaoNaoEncontradaException(Long id) {
        super("Inscrição com ID " + id + " não foi encontrada.");
    }
}