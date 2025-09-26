package com.fontineleantunes.apirestful.model;

import jakarta.persistence.*;
// import lombok.*;
// import java.time.LocalDateTime;

@Entity
public class Inscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private java.time.LocalDateTime dataHora;

    @ManyToOne
    private Aluno aluno;

    @ManyToOne
    private Turma turma;

    public Inscricao() {}
    public Inscricao(Long id, java.time.LocalDateTime dataHora, Aluno aluno, Turma turma) {
        this.id = id;
        this.dataHora = dataHora;
        this.aluno = aluno;
        this.turma = turma;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public java.time.LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(java.time.LocalDateTime dataHora) { this.dataHora = dataHora; }
    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }
    public Turma getTurma() { return turma; }
    public void setTurma(Turma turma) { this.turma = turma; }
}
