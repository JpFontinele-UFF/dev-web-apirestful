// Este repositório permite acessar, salvar, atualizar e remover inscrições no banco de dados.
// Herdando de JpaRepository, você já tem todos os métodos CRUD prontos para usar.
package com.fontineleantunes.apirestful.repository;

import com.fontineleantunes.apirestful.model.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
	List<Inscricao> findByTurma_Id(Long turmaId);

	// Recupera inscricoes de uma turma ordenadas pelo id (desc), para exibir o mais recente primeiro
	List<Inscricao> findByTurma_IdOrderByIdDesc(Long turmaId);
}
