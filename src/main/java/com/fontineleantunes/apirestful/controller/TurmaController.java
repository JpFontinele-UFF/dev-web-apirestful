package com.fontineleantunes.apirestful.controller;

import com.fontineleantunes.apirestful.model.Turma;
import com.fontineleantunes.apirestful.model.Professor;
import com.fontineleantunes.apirestful.dto.TurmaDTO;
import com.fontineleantunes.apirestful.service.TurmaService;
import com.fontineleantunes.apirestful.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;
    @Autowired
    private ProfessorService professorService;


    @PostMapping
    public ResponseEntity<?> create(@RequestBody TurmaDTO turmaDTO) {
        Professor professor = null;
        if (turmaDTO.getProfessorId() != null) {
            professor = professorService.findById(turmaDTO.getProfessorId()).orElse(null);
        }
        if (professor == null) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Professor não encontrado", turmaDTO));
        }
        Turma turma = new Turma();
        turma.setAno(turmaDTO.getAno());
        turma.setPeriodo(turmaDTO.getPeriodo());
        turma.setProfessor(professor);
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
