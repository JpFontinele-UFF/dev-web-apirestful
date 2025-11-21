package com.fontineleantunes.apirestful.service;

import com.fontineleantunes.apirestful.model.Aluno;
import com.fontineleantunes.apirestful.repository.AlunoRepository;
import com.fontineleantunes.apirestful.exception.CpfJaUtilizadoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository alunoRepository;

    public List<Aluno> findAll() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> findById(Long id) {
        return alunoRepository.findById(id);
    }

    public Aluno save(Aluno aluno) {
        // Validação de CPF: no create verificar existência, no update verificar conflito com outro registro
        if (aluno.getCpf() != null) {
            if (aluno.getId() == null) {
                // criação
                if (alunoRepository.existsByCpf(aluno.getCpf())) {
                    throw new CpfJaUtilizadoException("esse cpf ja foi utilizado");
                }
            } else {
                // atualização
                if (alunoRepository.existsByCpfAndIdNot(aluno.getCpf(), aluno.getId())) {
                    throw new CpfJaUtilizadoException("esse cpf ja foi utilizado");
                }
            }
        }
        return alunoRepository.save(aluno);
    }

    public void deleteById(Long id) {
        alunoRepository.deleteById(id);
    }

    // Recupera alunos que NÃO estão inscritos na turma informada
    public List<Aluno> findAlunosNotInTurma(Long turmaId) {
        return alunoRepository.findAlunosNotInTurma(turmaId);
    }
}
