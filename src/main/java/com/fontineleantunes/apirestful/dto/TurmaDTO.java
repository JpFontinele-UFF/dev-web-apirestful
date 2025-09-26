package com.fontineleantunes.apirestful.dto;

public class TurmaDTO {
    private int ano;
    private String periodo;
    private Long professorId;

    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }
    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }
    public Long getProfessorId() { return professorId; }
    public void setProfessorId(Long professorId) { this.professorId = professorId; }
}