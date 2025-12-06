package com.fontineleantunes.apirestful.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;

public class TurmaDTO {
    @NotNull(message = "Ano é obrigatório")
    @Min(value = 2000, message = "Ano deve ser maior que 2000")
    @Max(value = 2100, message = "Ano deve ser menor que 2100")
    private int ano;

    @NotBlank(message = "Período é obrigatório")
    @Pattern(regexp = "^(1|2)$", message = "Período deve ser 1 ou 2")
    private String periodo;

    @NotNull(message = "Professor é obrigatório")
    private Long professorId;

    @NotNull(message = "Disciplina é obrigatória")
    private Long disciplinaId;

    private String codigoTurma;

    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }
    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }
    public Long getProfessorId() { return professorId; }
    public void setProfessorId(Long professorId) { this.professorId = professorId; }
    public Long getDisciplinaId() { return disciplinaId; }
    public void setDisciplinaId(Long disciplinaId) { this.disciplinaId = disciplinaId; }
    public String getCodigoTurma() { return codigoTurma; }
    public void setCodigoTurma(String codigoTurma) { this.codigoTurma = codigoTurma; }
}