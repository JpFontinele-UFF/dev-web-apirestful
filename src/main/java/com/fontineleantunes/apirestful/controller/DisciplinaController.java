package com.fontineleantunes.apirestful.controller;

import com.fontineleantunes.apirestful.model.Disciplina;
import com.fontineleantunes.apirestful.service.DisciplinaService;
import com.fontineleantunes.apirestful.dto.DisciplinaDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disciplinas")
@Validated
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @GetMapping
    public ResponseEntity<List<Disciplina>> listarTodas() {
        List<Disciplina> disciplinas = disciplinaService.listarTodas();
        return ResponseEntity.ok(disciplinas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Disciplina> buscarPorId(@PathVariable Long id) {
        Disciplina disciplina = disciplinaService.buscarPorId(id);
        return ResponseEntity.ok(disciplina);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> criar(@Valid @RequestBody DisciplinaDTO disciplinaDTO) {
        Disciplina disciplina = new Disciplina();
        disciplina.setNome(disciplinaDTO.getNome());
        disciplina.setCodigo(disciplinaDTO.getCodigo());
        Disciplina novaDisciplina = disciplinaService.salvar(disciplina);
        ApiResponse response = new ApiResponse("Disciplina criada com sucesso", novaDisciplina);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> atualizar(@PathVariable Long id, @Valid @RequestBody DisciplinaDTO disciplinaDTO) {
        Disciplina disciplina = new Disciplina();
        disciplina.setNome(disciplinaDTO.getNome());
        disciplina.setCodigo(disciplinaDTO.getCodigo());
        Disciplina disciplinaAtualizada = disciplinaService.atualizar(id, disciplina);
        ApiResponse response = new ApiResponse("Disciplina atualizada com sucesso", disciplinaAtualizada);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletar(@PathVariable Long id) {
        disciplinaService.deletar(id);
        ApiResponse response = new ApiResponse("Disciplina deletada com sucesso", null);
        return ResponseEntity.ok(response);
    }

    public static class ApiResponse {
        private String message;
        private Object data;

        public ApiResponse(String message, Object data) {
            this.message = message;
            this.data = data;
        }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public Object getData() { return data; }
        public void setData(Object data) { this.data = data; }
    }
}
