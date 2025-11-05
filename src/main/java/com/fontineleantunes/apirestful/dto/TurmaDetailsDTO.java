package com.fontineleantunes.apirestful.dto;

import java.util.List;

public class TurmaDetailsDTO {
    private Long id;
    private int ano;
    private String periodo;
    private String disciplinaNome;
    private String professorNome;
    private List<AlunoInscrito> alunos;

    public static class AlunoInscrito {
        private Long inscricaoId; // 👈 ALTERAÇÃO AQUI
        private Long id;
        private String nome;
        private String email;
        private String cpf;

        public AlunoInscrito() {}

        // 👈 ALTERAÇÃO AQUI (adicionado 'inscricaoId' ao construtor)
        public AlunoInscrito(Long inscricaoId, Long id, String nome, String email, String cpf) { 
            this.inscricaoId = inscricaoId; 
            this.id = id; 
            this.nome = nome; 
            this.email = email; 
            this.cpf = cpf; 
        }

        public Long getInscricaoId() { return inscricaoId; } // 👈 ALTERAÇÃO AQUI
        public void setInscricaoId(Long inscricaoId) { this.inscricaoId = inscricaoId; }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getCpf() { return cpf; }
        public void setCpf(String cpf) { this.cpf = cpf; }
    }

    public TurmaDetailsDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }
    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }
    public String getDisciplinaNome() { return disciplinaNome; }
    public void setDisciplinaNome(String disciplinaNome) { this.disciplinaNome = disciplinaNome; }
    public String getProfessorNome() { return professorNome; }
    public void setProfessorNome(String professorNome) { this.professorNome = professorNome; }
    public List<AlunoInscrito> getAlunos() { return alunos; }
    public void setAlunos(List<AlunoInscrito> alunos) { this.alunos = alunos; }
}