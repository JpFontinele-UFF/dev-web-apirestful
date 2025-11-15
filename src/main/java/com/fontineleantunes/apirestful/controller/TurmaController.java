package com.fontineleantunes.apirestful.controller;

import com.fontineleantunes.apirestful.model.Turma;
import com.fontineleantunes.apirestful.model.Professor;
import com.fontineleantunes.apirestful.model.Disciplina;
import com.fontineleantunes.apirestful.dto.TurmaDTO;
import com.fontineleantunes.apirestful.service.InscricaoService;
import com.fontineleantunes.apirestful.service.TurmaService;
import com.fontineleantunes.apirestful.service.ProfessorService;
import com.fontineleantunes.apirestful.service.DisciplinaService;
import com.fontineleantunes.apirestful.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Autowired
    private AlunoService alunoService;

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

        // Verifica unicidade do codigoTurma se informado
        if (turmaDTO.getCodigoTurma() != null && !turmaDTO.getCodigoTurma().isEmpty()) {
            if (turmaService.findByCodigoTurma(turmaDTO.getCodigoTurma()).isPresent()) {
                return ResponseEntity.badRequest().body(new ApiResponse(false, "codigoTurma já existe", turmaDTO));
            }
        }

        Turma turma = new Turma();
        turma.setProfessor(professor);
        turma.setDisciplina(disciplina);
        turma.setAno(turmaDTO.getAno());
        turma.setPeriodo(turmaDTO.getPeriodo());
        turma.setCodigoTurma(turmaDTO.getCodigoTurma());
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

    // Agora suporta query param ?disciplinaId= para filtro eficiente pelo backend
    @GetMapping
    public ResponseEntity<?> listAll(@RequestParam(value = "disciplinaId", required = false) Long disciplinaId) {
        List<Turma> turmas;
        if (disciplinaId != null) {
            turmas = turmaService.findByDisciplinaId(disciplinaId);
            return ResponseEntity.ok(new ApiResponse(true, "Lista de turmas filtrada por disciplina", turmas));
        }
        turmas = turmaService.findAll();
        return ResponseEntity.ok(new ApiResponse(true, "Lista de turmas", turmas));
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
            dto.setCodigoTurma(turma.getCodigoTurma());

            // Recupera inscricoes ordenadas pelo id desc para garantir que o aluno mais recente fique no topo
            java.util.List<com.fontineleantunes.apirestful.model.Inscricao> inscricoes = inscricaoService.findByTurmaIdOrderByIdDesc(turma.getId());
            java.util.List<com.fontineleantunes.apirestful.dto.TurmaDetailsDTO.AlunoInscrito> alunos = new java.util.ArrayList<>();
            for (com.fontineleantunes.apirestful.model.Inscricao ins : inscricoes) {
                if (ins.getAluno() != null) {
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

    // Novo endpoint para retornar todos os alunos de uma turma (ordenados pelo id da inscrição desc)
    @GetMapping("/{id}/alunos")
    public ResponseEntity<?> getAlunosPorTurma(@PathVariable Long id) {
        java.util.List<com.fontineleantunes.apirestful.model.Inscricao> inscricoes = inscricaoService.findByTurmaIdOrderByIdDesc(id);
        java.util.List<com.fontineleantunes.apirestful.model.Aluno> alunos = new java.util.ArrayList<>();
        for (com.fontineleantunes.apirestful.model.Inscricao ins : inscricoes) {
            if (ins.getAluno() != null) {
                alunos.add(ins.getAluno());
            }
        }
        return ResponseEntity.ok(new ApiResponse(true, "Alunos da turma", alunos));
    }

    // Endpoint solicitado: lista alunos que NÃO estão inscritos na turma — otimiza chamada do front
    @GetMapping("/{id}/alunos-nao-inscritos")
    public ResponseEntity<?> getAlunosNaoInscritos(@PathVariable Long id) {
        java.util.List<com.fontineleantunes.apirestful.model.Aluno> alunos = alunoService.findAlunosNotInTurma(id);
        return ResponseEntity.ok(new ApiResponse(true, "Alunos não inscritos na turma", alunos));
    }

     // Novo endpoint: lista de turmas por disciplina (usado pelo TurmaComboBox)
     @GetMapping("/disciplina/{disciplinaId}")
     public ResponseEntity<?> getTurmasPorDisciplina(@PathVariable Long disciplinaId) {
         List<Turma> turmas = turmaService.findByDisciplinaId(disciplinaId);
         return ResponseEntity.ok(turmas);
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
