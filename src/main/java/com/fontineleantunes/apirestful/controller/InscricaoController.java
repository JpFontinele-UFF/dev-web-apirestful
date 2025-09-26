package com.fontineleantunes.apirestful.controller;

import com.fontineleantunes.apirestful.model.Inscricao;
import com.fontineleantunes.apirestful.model.Aluno;
import com.fontineleantunes.apirestful.model.Turma;
import com.fontineleantunes.apirestful.dto.InscricaoDTO;
import com.fontineleantunes.apirestful.service.InscricaoService;
import com.fontineleantunes.apirestful.service.AlunoService;
import com.fontineleantunes.apirestful.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inscricoes")
public class InscricaoController {

    @Autowired
    private InscricaoService inscricaoService;
    @Autowired
    private AlunoService alunoService;
    @Autowired
    private TurmaService turmaService;


    @PostMapping
    public ResponseEntity<?> create(@RequestBody InscricaoDTO dto) {
        Aluno aluno = null;
        Turma turma = null;
        if (dto.getAlunoId() != null) {
            aluno = alunoService.findById(dto.getAlunoId()).orElse(null);
        }
        if (dto.getTurmaId() != null) {
            turma = turmaService.findById(dto.getTurmaId()).orElse(null);
        }
        if (aluno == null || turma == null) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Aluno ou Turma não encontrado", dto));
        }
        Inscricao inscricao = new Inscricao();
        inscricao.setAluno(aluno);
        inscricao.setTurma(turma);
        inscricao.setDataHora(java.time.LocalDateTime.now());
        Inscricao saved = inscricaoService.save(inscricao);
        return ResponseEntity.ok(new ApiResponse(true, "Inscrição cadastrada com sucesso", saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        inscricaoService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse(true, "Inscrição removida com sucesso", null));
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
