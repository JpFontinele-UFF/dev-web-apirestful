// Este repositório permite acessar, salvar, atualizar e remover alunos no banco de dados.
// Herdando de JpaRepository, você já tem todos os métodos CRUD prontos para usar.
package com.fontineleantunes.apirestful.repository;

import com.fontineleantunes.apirestful.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    // Recupera alunos que NÃO estão inscritos na turma informada
    @Query("select a from Aluno a where a.id not in (select i.aluno.id from Inscricao i where i.turma.id = :turmaId)")
    List<Aluno> findAlunosNotInTurma(@Param("turmaId") Long turmaId);

    // Verifica existência de CPF (para evitar duplicidade)
    boolean existsByCpf(String cpf);

    // Verifica se existe outro aluno com o mesmo CPF (usado em update)
    boolean existsByCpfAndIdNot(String cpf, Long id);

    Optional<Aluno> findByCpf(String cpf);
}
