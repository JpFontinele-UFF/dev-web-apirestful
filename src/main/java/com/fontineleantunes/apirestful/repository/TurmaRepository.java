
// Este repositório permite acessar, salvar, atualizar e remover turmas no banco de dados.
// Herdando de JpaRepository, você já tem todos os métodos CRUD prontos para usar.
package com.fontineleantunes.apirestful.repository;

import com.fontineleantunes.apirestful.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
}
