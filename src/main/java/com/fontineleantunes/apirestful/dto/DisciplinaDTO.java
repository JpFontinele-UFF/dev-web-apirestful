package com.fontineleantunes.apirestful.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DisciplinaDTO {

    @NotBlank(message = "Nome da disciplina é obrigatório")
    @Size(min = 3, message = "Nome deve ter no mínimo 3 caracteres")
    private String nome;

    @NotBlank(message = "Código da disciplina é obrigatório")
    @Size(min = 2, max = 10, message = "Código deve ter entre 2 e 10 caracteres")
    private String codigo;

    public DisciplinaDTO() {}

    public DisciplinaDTO(String nome, String codigo) {
        this.nome = nome;
        this.codigo = codigo;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
}
