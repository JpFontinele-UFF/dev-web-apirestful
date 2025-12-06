package com.fontineleantunes.apirestful.dto;

import jakarta.validation.constraints.NotNull;

public class InscricaoDTO {
    @NotNull(message = "ID do aluno é obrigatório")
    private Long alunoId;

    @NotNull(message = "ID da turma é obrigatório")
    private Long turmaId;

    public Long getAlunoId() { return alunoId; }
    public void setAlunoId(Long alunoId) { this.alunoId = alunoId; }
    public Long getTurmaId() { return turmaId; }
    public void setTurmaId(Long turmaId) { this.turmaId = turmaId; }
}