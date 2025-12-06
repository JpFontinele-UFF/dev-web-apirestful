package com.fontineleantunes.apirestful.config;

import com.fontineleantunes.apirestful.auth.model.Role;
import com.fontineleantunes.apirestful.auth.model.Usuario;
import com.fontineleantunes.apirestful.auth.repository.UsuarioRepository;
import com.fontineleantunes.apirestful.model.Aluno;
import com.fontineleantunes.apirestful.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!usuarioRepository.existsByUsername("admin@mail.com")) {
            Usuario admin = new Usuario(
                    "admin@mail.com",
                    passwordEncoder.encode("password"),
                    "Administrador",
                    Set.of(Role.ROLE_ADMIN)
            );
            usuarioRepository.save(admin);
        }

        // garantir um usuário padrão USER de exemplo
        if (!usuarioRepository.existsByUsername("user@mail.com")) {
            Usuario user = new Usuario(
                    "user@mail.com",
                    passwordEncoder.encode("password"),
                    "Usuário Padrão",
                    Set.of(Role.ROLE_USER)
            );
            usuarioRepository.save(user);
        }

        // Criar alunos de teste sem inscrições (podem ser removidos)
        if (alunoRepository.count() == 0) {
            String[] nomes = {"João Silva", "Maria Santos", "Carlos Oliveira", "Ana Costa", "Pedro Ferreira", 
                              "Juliana Lima", "Roberto Gomes", "Fernanda Alves", "Lucas Martins", "Sophia Rocha"};
            String[] emails = {"joao.silva@example.com", "maria.santos@example.com", "carlos.oliveira@example.com",
                               "ana.costa@example.com", "pedro.ferreira@example.com", "juliana.lima@example.com",
                               "roberto.gomes@example.com", "fernanda.alves@example.com", "lucas.martins@example.com",
                               "sophia.rocha@example.com"};
            String[] cpfs = {"12345678901", "12345678902", "12345678903", "12345678904", "12345678905",
                             "12345678906", "12345678907", "12345678908", "12345678909", "12345678910"};

            for (int i = 0; i < nomes.length; i++) {
                Aluno aluno = new Aluno();
                aluno.setNome(nomes[i]);
                aluno.setEmail(emails[i]);
                aluno.setCpf(cpfs[i]);
                alunoRepository.save(aluno);
            }
        }
    }
}
