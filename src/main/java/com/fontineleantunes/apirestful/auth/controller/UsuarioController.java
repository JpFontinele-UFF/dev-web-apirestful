package com.fontineleantunes.apirestful.auth.controller;

import com.fontineleantunes.apirestful.auth.model.Role;
import com.fontineleantunes.apirestful.auth.model.Usuario;
import com.fontineleantunes.apirestful.auth.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/auth/admin")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> criarUsuario(@RequestBody Map<String, String> body) {

        String username = body.getOrDefault("username", body.get("email"));
        String password = body.get("password");
        String nome = body.get("nome");
        String role = body.getOrDefault("role", "ROLE_USER");

        // Validações
        if (username == null || username.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Username é obrigatório"));
        }

        if (password == null || password.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Senha é obrigatória"));
        }

        if (nome == null || nome.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Nome é obrigatório"));
        }

        // Verificar se username já existe
        if (usuarioRepository.existsByUsername(username)) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Username já cadastrado"));
        }

        // Validar role - aceita "Usuário"/"Administrador" ou "ROLE_USER"/"ROLE_ADMIN"
        String normalizedRole = role.toUpperCase().trim();
        
        if (normalizedRole.contains("ADMIN") || normalizedRole.contains("ADMINISTRADOR")) {
            role = "ROLE_ADMIN";
        } else if (normalizedRole.contains("USER") || normalizedRole.contains("USUÁRIO") || normalizedRole.contains("USUARIO")) {
            role = "ROLE_USER";
        } else {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Role inválida. Use 'Usuário' ou 'Administrador'"));
        }

        try {
            // Criar novo usuário
            Set<Role> roles = new HashSet<>();
            roles.add(Role.valueOf(role));

            Usuario usuario = new Usuario(
                    username,
                    passwordEncoder.encode(password),
                    nome,
                    roles
            );

            usuario = usuarioRepository.save(usuario);

            return ResponseEntity.status(201).body(Map.of(
                    "success", true,
                    "message", "Usuário cadastrado com sucesso",
                    "data", Map.of(
                            "id", usuario.getId(),
                            "username", usuario.getUsername(),
                            "nome", usuario.getNome(),
                            "roles", usuario.getRoles()
                    )
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "success", false,
                    "message", "Erro ao cadastrar usuário: " + e.getMessage()
            ));
        }
    }

    @GetMapping("/usuarios")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listarUsuarios() {
        try {
            List<Usuario> usuarios = usuarioRepository.findAll();
            List<Map<String, Object>> usuariosDTO = new ArrayList<>();

            for (Usuario u : usuarios) {
                usuariosDTO.add(Map.of(
                        "id", u.getId(),
                        "username", u.getUsername(),
                        "nome", u.getNome(),
                        "roles", u.getRoles()
                ));
            }

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Usuários listados com sucesso",
                    "data", usuariosDTO
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "success", false,
                    "message", "Erro ao listar usuários: " + e.getMessage()
            ));
        }
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> obterUsuarioAtual() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);

            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "data", Map.of(
                                "id", usuario.getId(),
                                "username", usuario.getUsername(),
                                "nome", usuario.getNome(),
                                "roles", usuario.getRoles()
                        )
                ));
            }

            return ResponseEntity.status(404).body(Map.of(
                    "success", false,
                    "message", "Usuário não encontrado"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "success", false,
                    "message", "Erro ao obter usuário: " + e.getMessage()
            ));
        }
    }

    @PutMapping("/usuarios/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(404).body(Map.of(
                        "success", false,
                        "message", "Usuário não encontrado"
                ));
            }

            Usuario usuario = usuarioOpt.get();

            // Atualizar nome se fornecido
            if (body.containsKey("nome") && !body.get("nome").isBlank()) {
                usuario.setNome(body.get("nome"));
            }

            // Atualizar role se fornecida
            if (body.containsKey("role") && !body.get("role").isBlank()) {
                String roleValue = body.get("role");
                String normalizedRole = roleValue.toUpperCase().trim();
                
                // Aceita "Usuário"/"Administrador" ou "ROLE_USER"/"ROLE_ADMIN"
                if (normalizedRole.contains("ADMIN") || normalizedRole.contains("ADMINISTRADOR")) {
                    roleValue = "ROLE_ADMIN";
                } else if (normalizedRole.contains("USER") || normalizedRole.contains("USUÁRIO") || normalizedRole.contains("USUARIO")) {
                    roleValue = "ROLE_USER";
                } else {
                    return ResponseEntity.badRequest().body(Map.of(
                            "success", false,
                            "message", "Role inválida. Use 'Usuário' ou 'Administrador'"
                    ));
                }
                
                Set<Role> roles = new HashSet<>();
                roles.add(Role.valueOf(roleValue));
                usuario.setRoles(roles);
            }

            usuario = usuarioRepository.save(usuario);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Usuário atualizado com sucesso",
                    "data", Map.of(
                            "id", usuario.getId(),
                            "username", usuario.getUsername(),
                            "nome", usuario.getNome(),
                            "roles", usuario.getRoles()
                    )
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "success", false,
                    "message", "Erro ao atualizar usuário: " + e.getMessage()
            ));
        }
    }

    @DeleteMapping("/usuarios/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id) {
        try {
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(404).body(Map.of(
                        "success", false,
                        "message", "Usuário não encontrado"
                ));
            }

            usuarioRepository.deleteById(id);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Usuário deletado com sucesso"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "success", false,
                    "message", "Erro ao deletar usuário: " + e.getMessage()
            ));
        }
    }
}
