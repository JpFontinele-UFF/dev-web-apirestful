package com.fontineleantunes.apirestful.auth.repository;

import com.fontineleantunes.apirestful.auth.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}
