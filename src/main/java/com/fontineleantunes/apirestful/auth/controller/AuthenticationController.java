package com.fontineleantunes.apirestful.auth.controller;

import com.fontineleantunes.apirestful.auth.model.Role;
import com.fontineleantunes.apirestful.auth.model.Usuario;
import com.fontineleantunes.apirestful.auth.repository.UsuarioRepository;
import com.fontineleantunes.apirestful.auth.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Object principal = authentication.getPrincipal();
        String token = "";
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails ud) {
            token = jwtService.gerarToken(ud);
        }

        // find user to return basic info
        Usuario user = usuarioRepository.findByUsername(username).orElse(null);

        return ResponseEntity.ok(Map.of(
                "token", token,
                "user", user == null ? Collections.emptyMap() : Map.of(
                        "id", user.getId(),
                        "nome", user.getNome(),
                        "username", user.getUsername(),
                        "roles", user.getRoles()
                )
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        String nome = body.get("nome");

        if (usuarioRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username já está em uso"));
        }

        Usuario usuario = new Usuario(username, passwordEncoder.encode(password), nome, Set.of(Role.USER));
        usuarioRepository.save(usuario);
        return ResponseEntity.status(201).body(Map.of("id", usuario.getId(), "username", usuario.getUsername(), "nome", usuario.getNome(), "roles", usuario.getRoles()));
    }

    @PostMapping("/register-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerAdmin(@RequestBody Map<String, Object> body) {
        String username = (String) body.get("username");
        String password = (String) body.get("password");
        String nome = (String) body.get("nome");
        Object rolesObj = body.get("roles");

        if (usuarioRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username já está em uso"));
        }

        Set<Role> roles = Set.of(Role.USER);
        if (rolesObj instanceof Iterable<?> iterable) {
            // not robust parsing, but sufficient for simple payload
            for (Object r : iterable) {
                if ("ADMIN".equals(r)) roles = Set.of(Role.ADMIN);
            }
        }

        Usuario usuario = new Usuario(username, passwordEncoder.encode(password), nome, roles);
        usuarioRepository.save(usuario);
        return ResponseEntity.status(201).body(Map.of("id", usuario.getId(), "username", usuario.getUsername(), "nome", usuario.getNome(), "roles", usuario.getRoles()));
    }
}
