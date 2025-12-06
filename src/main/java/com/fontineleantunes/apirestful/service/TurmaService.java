package com.fontineleantunes.apirestful.service;

import com.fontineleantunes.apirestful.model.Turma;
import com.fontineleantunes.apirestful.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class TurmaService {
    @Autowired
    private TurmaRepository turmaRepository;


    public Turma save(Turma turma) {
        return turmaRepository.save(turma);
    }

    public Optional<Turma> findById(Long id) {
        return turmaRepository.findById(id);
    }

    public List<Turma> findAll() {
        return turmaRepository.findAll();
    }

    public void deleteById(Long id) {
        turmaRepository.deleteById(id);
    }

    public Optional<Turma> findByCodigoTurma(String codigoTurma) {
        return turmaRepository.findByCodigoTurma(codigoTurma);
    }

    // Recupera todas as turmas de uma disciplina
    public List<Turma> findByDisciplinaId(Long disciplinaId) {
        return turmaRepository.findByDisciplina_Id(disciplinaId);
    }
}
