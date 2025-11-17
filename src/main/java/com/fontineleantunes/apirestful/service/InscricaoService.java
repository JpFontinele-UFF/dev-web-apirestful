package com.fontineleantunes.apirestful.service;

import com.fontineleantunes.apirestful.model.Inscricao;
import com.fontineleantunes.apirestful.repository.InscricaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.fontineleantunes.apirestful.repository.InscricaoRepository;

@Service
public class InscricaoService {
    @Autowired
    private InscricaoRepository inscricaoRepository;

    public Inscricao save(Inscricao inscricao) {
        return inscricaoRepository.save(inscricao);
    }

    public void deleteById(Long id) {
        inscricaoRepository.deleteById(id);
    }

    // Retorna todas as inscricoes associadas a uma turma
    public List<com.fontineleantunes.apirestful.model.Inscricao> findByTurmaId(Long turmaId) {
        return inscricaoRepository.findByTurma_Id(turmaId);
    }
}
