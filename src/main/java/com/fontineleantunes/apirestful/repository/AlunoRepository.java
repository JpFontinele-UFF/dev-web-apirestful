
// Este repositório permite acessar, salvar, atualizar e remover alunos no banco de dados.
// Herdando de JpaRepository, você já tem todos os métodos CRUD prontos para usar.
package com.fontineleantunes.apirestful.repository;

import com.fontineleantunes.apirestful.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
