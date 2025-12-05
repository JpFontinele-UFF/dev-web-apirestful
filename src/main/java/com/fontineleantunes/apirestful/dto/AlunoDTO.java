package com.fontineleantunes.apirestful.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AlunoDTO {

    @NotBlank
    @Size(min = 3)
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String cpf;

    public AlunoDTO() {}
    public AlunoDTO(String nome, String email, String cpf) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
}
