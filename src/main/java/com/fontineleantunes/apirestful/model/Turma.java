package com.fontineleantunes.apirestful.model;

import jakarta.persistence.*;
// import lombok.*;

@Entity
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int ano;
    private String periodo;

    @ManyToOne
    private Professor professor;

    @ManyToOne
    private Disciplina disciplina;

    public Turma() {}
    public Turma(Long id, int ano, String periodo, Professor professor, Disciplina disciplina) {
        this.id = id;
        this.ano = ano;
        this.periodo = periodo;
        this.professor = professor;
        this.disciplina = disciplina;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }
    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }
    public Professor getProfessor() { return professor; }
    public void setProfessor(Professor professor) { this.professor = professor; }
    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }
}
