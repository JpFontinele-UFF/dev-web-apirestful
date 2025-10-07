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

    // Permite buscar uma turma pelo ID, retornando Optional<Turma>.
    // Isso é importante para validar se a turma existe antes de criar uma inscrição ou outro vínculo.
    public Optional<Turma> findById(Long id) {
        return turmaRepository.findById(id);
    }

    public List<Turma> findAll() {
        return turmaRepository.findAll();
    }

    public void deleteById(Long id) {
        turmaRepository.deleteById(id);
    }
}
