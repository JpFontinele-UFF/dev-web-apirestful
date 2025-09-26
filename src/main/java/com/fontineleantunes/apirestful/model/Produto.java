package com.fontineleantunes.apirestful.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString

// @Data // get, set, toString, hashCode, equals

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imagem;
    private String nome;
    private String slug;
    private String descricao;
    private boolean disponivel;
    private int qtdEstoque;
    private BigDecimal preco;
    private LocalDate dataCadastro;
    @ManyToOne(fetch = FetchType.LAZY)
    private Categoria categoria;

//    public Produto() {
//
//    }

    public Produto(String imagem, String nome, String slug,
                   String descricao, boolean disponivel, int qtdEstoque,
                   BigDecimal preco, LocalDate dataCadastro, Categoria categoria) {
        this.imagem = imagem;
        this.nome = nome;
        this.slug = slug;
        this.descricao = descricao;
        this.disponivel = disponivel;
        this.qtdEstoque = qtdEstoque;
        this.preco = preco;
        this.dataCadastro = dataCadastro;
        this.categoria = categoria;
    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getImagem() {
//        return imagem;
//    }
//
//    public void setImagem(String imagem) {
//        this.imagem = imagem;
//    }
//
//    public String getNome() {
//        return nome;
//    }
//
//    public void setNome(String nome) {
//        this.nome = nome;
//    }
//
//    public String getSlug() {
//        return slug;
//    }
//
//    public void setSlug(String slug) {
//        this.slug = slug;
//    }
//
//    public String getDescricao() {
//        return descricao;
//    }
//
//    public void setDescricao(String descricao) {
//        this.descricao = descricao;
//    }
//
//    public boolean isDisponivel() {
//        return disponivel;
//    }
//
//    public void setDisponivel(boolean disponivel) {
//        this.disponivel = disponivel;
//    }
//
//    public int getQtdEstoque() {
//        return qtdEstoque;
//    }
//
//    public void setQtdEstoque(int qtdEstoque) {
//        this.qtdEstoque = qtdEstoque;
//    }
//
//    public BigDecimal getPreco() {
//        return preco;
//    }
//
//    public void setPreco(BigDecimal preco) {
//        this.preco = preco;
//    }
//
//    public LocalDate getDataCadastro() {
//        return dataCadastro;
//    }
//
//    public void setDataCadastro(LocalDate dataCadastro) {
//        this.dataCadastro = dataCadastro;
//    }
//
//    public String getCategoria() {
//        return categoria;
//    }
//
//    public void setCategoria(String categoria) {
//        this.categoria = categoria;
//    }
}
