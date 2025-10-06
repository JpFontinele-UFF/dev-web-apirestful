package com.fontineleantunes.apirestful.exception;

public class DisciplinaNaoEncontradaException extends EntidadeNaoEncontradaException {
    public DisciplinaNaoEncontradaException(Long id) {
        super("Disciplina não encontrada com o id: " + id);
    }
}
