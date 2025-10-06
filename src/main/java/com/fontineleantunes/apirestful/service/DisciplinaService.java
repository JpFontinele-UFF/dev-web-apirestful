package com.fontineleantunes.apirestful.service;

import com.fontineleantunes.apirestful.exception.DisciplinaNaoEncontradaException;
import com.fontineleantunes.apirestful.model.Disciplina;
import com.fontineleantunes.apirestful.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    public List<Disciplina> listarTodas() {
        return disciplinaRepository.findAll();
    }

    public Disciplina buscarPorId(Long id) {
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new DisciplinaNaoEncontradaException(id));
    }

    public Disciplina salvar(Disciplina disciplina) {
        return disciplinaRepository.save(disciplina);
    }

    public Disciplina atualizar(Long id, Disciplina disciplinaAtualizada) {
        Disciplina disciplina = buscarPorId(id);
        disciplina.setNome(disciplinaAtualizada.getNome());
        disciplina.setCargaHoraria(disciplinaAtualizada.getCargaHoraria());
        return disciplinaRepository.save(disciplina);
    }

    public void deletar(Long id) {
        Disciplina disciplina = buscarPorId(id);
        disciplinaRepository.delete(disciplina);
    }
}
