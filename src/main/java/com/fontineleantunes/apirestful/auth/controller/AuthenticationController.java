package com.fontineleantunes.apirestful.auth.controller;

import com.fontineleantunes.apirestful.auth.model.Role;
import com.fontineleantunes.apirestful.auth.model.Usuario;
import com.fontineleantunes.apirestful.auth.repository.UsuarioRepository;
import com.fontineleantunes.apirestful.auth.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        // Frontend pode enviar "email" como login; usamos como username
        String username = body.getOrDefault("username", body.get("email"));
        String password = body.get("password");
        String nome = body.get("nome");

        if (username == null || username.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Informe um username ou email"));
        }

        if (usuarioRepository.existsByUsername(username)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username já cadastrado"));
        }

        Usuario usuario = new Usuario(username, passwordEncoder.encode(password), nome, Set.of(Role.ROLE_USER));
        usuarioRepository.save(usuario);
        return ResponseEntity.status(201).body(Map.of(
                "id", usuario.getId(),
            "email", usuario.getUsername(),
                "nome", usuario.getNome(),
                "roles", usuario.getRoles()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        // Aceita "username" ou "email" como login
        String username = body.getOrDefault("username", body.get("email"));
        String password = body.get("password");

        if (username == null || username.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Informe email ou username"));
        }

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body(Map.of("message", "Email ou senha incorretos"));
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Object principal = authentication.getPrincipal();
        String token = "";
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails ud) {
            token = jwtService.gerarToken(ud);
        }

        Usuario user = usuarioRepository.findByUsername(username).orElse(null);
        return ResponseEntity.ok(Map.of(
                "token", token,
                "user", user == null ? Collections.emptyMap() : Map.of(
                        "id", user.getId(),
                        "nome", user.getNome(),
                "email", user.getUsername(),
                        "roles", user.getRoles()
                )
        ));
    }
}
