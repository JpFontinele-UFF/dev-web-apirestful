// Este repositório permite acessar, salvar, atualizar e remover turmas no banco de dados.
// Herdando de JpaRepository, você já tem todos os métodos CRUD prontos para usar.
package com.fontineleantunes.apirestful.repository;

import com.fontineleantunes.apirestful.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
    Optional<Turma> findByCodigoTurma(String codigoTurma);

    // Recupera todas as turmas de uma disciplina
    List<Turma> findByDisciplina_Id(Long disciplinaId);
}
