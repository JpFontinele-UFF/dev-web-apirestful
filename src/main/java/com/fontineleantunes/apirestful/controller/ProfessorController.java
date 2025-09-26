package com.fontineleantunes.apirestful.controller;

import com.fontineleantunes.apirestful.model.Professor;
import com.fontineleantunes.apirestful.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professores")
public class ProfessorController {
    @Autowired
    private ProfessorService professorService;


    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(new ApiResponse(true, "Lista de professores", professorService.findAll()));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return professorService.findById(id)
                .map(prof -> ResponseEntity.ok(new ApiResponse(true, "Professor encontrado", prof)))
                .orElse(ResponseEntity.status(404).body(new ApiResponse(false, "Professor não encontrado", null)));
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody Professor professor) {
        Professor saved = professorService.save(professor);
        return ResponseEntity.ok(new ApiResponse(true, "Professor cadastrado com sucesso", saved));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Professor professor) {
        if (!professorService.findById(id).isPresent()) {
            return ResponseEntity.status(404).body(new ApiResponse(false, "Professor não encontrado", null));
        }
        professor.setId(id);
        Professor saved = professorService.save(professor);
        return ResponseEntity.ok(new ApiResponse(true, "Professor alterado com sucesso", saved));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!professorService.findById(id).isPresent()) {
            return ResponseEntity.status(404).body(new ApiResponse(false, "Professor não encontrado", null));
        }
        try {
            professorService.deleteById(id);
            return ResponseEntity.ok(new ApiResponse(true, "Professor removido com sucesso", null));
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Não é possível remover o professor pois ele está vinculado a uma turma.", null));
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
