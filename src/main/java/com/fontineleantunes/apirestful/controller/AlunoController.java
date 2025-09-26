package com.fontineleantunes.apirestful.controller;

import com.fontineleantunes.apirestful.model.Aluno;
import com.fontineleantunes.apirestful.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {
    @Autowired
    private AlunoService alunoService;


    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(new ApiResponse(true, "Lista de alunos", alunoService.findAll()));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return alunoService.findById(id)
                .map(aluno -> ResponseEntity.ok(new ApiResponse(true, "Aluno encontrado", aluno)))
                .orElse(ResponseEntity.status(404).body(new ApiResponse(false, "Aluno não encontrado", null)));
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody Aluno aluno) {
        Aluno saved = alunoService.save(aluno);
        return ResponseEntity.ok(new ApiResponse(true, "Aluno cadastrado com sucesso", saved));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Aluno aluno) {
        if (!alunoService.findById(id).isPresent()) {
            return ResponseEntity.status(404).body(new ApiResponse(false, "Aluno não encontrado", null));
        }
        aluno.setId(id);
        Aluno saved = alunoService.save(aluno);
        return ResponseEntity.ok(new ApiResponse(true, "Aluno alterado com sucesso", saved));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!alunoService.findById(id).isPresent()) {
            return ResponseEntity.status(404).body(new ApiResponse(false, "Aluno não encontrado", null));
        }
        try {
            alunoService.deleteById(id);
            return ResponseEntity.ok(new ApiResponse(true, "Aluno removido com sucesso", null));
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Não é possível remover o aluno pois ele possui inscrições vinculadas.", null));
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
