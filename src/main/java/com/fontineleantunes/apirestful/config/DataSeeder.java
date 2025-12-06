package com.fontineleantunes.apirestful.config;

import com.fontineleantunes.apirestful.auth.model.Role;
import com.fontineleantunes.apirestful.auth.model.Usuario;
import com.fontineleantunes.apirestful.auth.repository.UsuarioRepository;
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
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!usuarioRepository.existsByUsername("admin")) {
            Usuario admin = new Usuario(
                    "admin",
                    passwordEncoder.encode("admin"),
                    "Administrador",
                    Set.of(Role.ROLE_ADMIN)
            );
            usuarioRepository.save(admin);
        }

        // garantir um usuário padrão USER de exemplo
        if (!usuarioRepository.existsByUsername("user")) {
            Usuario user = new Usuario(
                    "user",
                    passwordEncoder.encode("user"),
                    "Usuário Padrão",
                    Set.of(Role.ROLE_USER)
            );
            usuarioRepository.save(user);
        }
    }
}
