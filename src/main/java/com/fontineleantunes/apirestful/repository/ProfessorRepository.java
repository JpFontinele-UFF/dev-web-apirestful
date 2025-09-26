
// Este repositório permite acessar, salvar, atualizar e remover professores no banco de dados.
// Herdando de JpaRepository, você já tem todos os métodos CRUD prontos para usar.
package com.fontineleantunes.apirestful.repository;

import com.fontineleantunes.apirestful.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
