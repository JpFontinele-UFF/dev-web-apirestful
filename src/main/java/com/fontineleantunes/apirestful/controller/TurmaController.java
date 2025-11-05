package com.fontineleantunes.apirestful.controller;

import com.fontineleantunes.apirestful.model.Turma;
import com.fontineleantunes.apirestful.model.Professor;
import com.fontineleantunes.apirestful.model.Disciplina;
import com.fontineleantunes.apirestful.dto.TurmaDTO;
import com.fontineleantunes.apirestful.service.InscricaoService;
import com.fontineleantunes.apirestful.service.TurmaService;
import com.fontineleantunes.apirestful.service.ProfessorService;
import com.fontineleantunes.apirestful.service.DisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;
    @Autowired
    private InscricaoService inscricaoService;
    @Autowired
    private ProfessorService professorService;
    @Autowired
    private DisciplinaService disciplinaService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TurmaDTO turmaDTO) {
        Professor professor = null;
        if (turmaDTO.getProfessorId() != null) {
            professor = professorService.findById(turmaDTO.getProfessorId()).orElse(null);
        }
        if (professor == null) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Professor não encontrado", turmaDTO));
        }

        Disciplina disciplina = null;
        if (turmaDTO.getDisciplinaId() != null) {
            disciplina = disciplinaService.buscarPorId(turmaDTO.getDisciplinaId());
        }
        if (disciplina == null) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Disciplina não encontrada", turmaDTO));
        }

        Turma turma = new Turma();
        turma.setAno(turmaDTO.getAno());
        turma.setPeriodo(turmaDTO.getPeriodo());
        turma.setProfessor(professor);
        turma.setDisciplina(disciplina);
        Turma saved = turmaService.save(turma);
        return ResponseEntity.ok(new ApiResponse(true, "Turma cadastrada com sucesso", saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            turmaService.deleteById(id);
            return ResponseEntity.ok(new ApiResponse(true, "Turma removida com sucesso", null));
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Não é possível remover a turma pois ela possui inscrições vinculadas.", null));
        }
    }

    @GetMapping
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(new ApiResponse(true, "Lista de turmas", turmaService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return turmaService.findById(id).map(turma -> {
            com.fontineleantunes.apirestful.dto.TurmaDetailsDTO dto = new com.fontineleantunes.apirestful.dto.TurmaDetailsDTO();
            dto.setId(turma.getId());
            dto.setAno(turma.getAno());
            dto.setPeriodo(turma.getPeriodo());
            if (turma.getDisciplina() != null) dto.setDisciplinaNome(turma.getDisciplina().getNome());
            if (turma.getProfessor() != null) dto.setProfessorNome(turma.getProfessor().getNome());

            java.util.List<com.fontineleantunes.apirestful.model.Inscricao> inscricoes = inscricaoService.findByTurmaId(turma.getId());
            java.util.List<com.fontineleantunes.apirestful.dto.TurmaDetailsDTO.AlunoInscrito> alunos = new java.util.ArrayList<>();
            for (com.fontineleantunes.apirestful.model.Inscricao ins : inscricoes) {
                if (ins.getAluno() != null) {
                    
                    // 👈 ALTERAÇÃO AQUI (passando 'ins.getId()' como primeiro parâmetro)
                    alunos.add(new com.fontineleantunes.apirestful.dto.TurmaDetailsDTO.AlunoInscrito(
                        ins.getId(), 
                        ins.getAluno().getId(),
                        ins.getAluno().getNome(),
                        ins.getAluno().getEmail(),
                        ins.getAluno().getCpf()
                    ));
                }
            }
            dto.setAlunos(alunos);
            return ResponseEntity.ok(new ApiResponse(true, "Turma encontrada", dto));
        }).orElse(ResponseEntity.status(404).body(new ApiResponse(false, "Turma não encontrada", null)));
    }

    // Novo endpoint para retornar todos os alunos de uma turma
    @GetMapping("/{id}/alunos")
    public ResponseEntity<?> getAlunosPorTurma(@PathVariable Long id) {
        java.util.List<com.fontineleantunes.apirestful.model.Inscricao> inscricoes = inscricaoService.findByTurmaId(id);
        java.util.List<com.fontineleantunes.apirestful.model.Aluno> alunos = new java.util.ArrayList<>();
        for (com.fontineleantunes.apirestful.model.Inscricao ins : inscricoes) {
            if (ins.getAluno() != null) {
                alunos.add(ins.getAluno());
            }
        }
        return ResponseEntity.ok(alunos);
    }

    // Classe interna para resposta padrão
    public static class ApiResponse {
        private boolean success;
        private String message;
        private Object data;
        public ApiResponse(boolean success, String message, Object data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }
        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public Object getData() { return data; }
    }
}