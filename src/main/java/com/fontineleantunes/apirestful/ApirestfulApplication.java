package com.fontineleantunes.apirestful;

import com.fontineleantunes.apirestful.model.Aluno;
import com.fontineleantunes.apirestful.model.Professor;
import com.fontineleantunes.apirestful.model.Turma;
import com.fontineleantunes.apirestful.model.Inscricao;
import com.fontineleantunes.apirestful.repository.AlunoRepository;
import com.fontineleantunes.apirestful.repository.ProfessorRepository;
import com.fontineleantunes.apirestful.repository.TurmaRepository;
import com.fontineleantunes.apirestful.repository.InscricaoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;

@SpringBootApplication
public class ApirestfulApplication {



    public static void main(String[] args) {
        SpringApplication.run(ApirestfulApplication.class, args);
    }

    // Nenhuma interface de console, apenas REST API disponível.
}
