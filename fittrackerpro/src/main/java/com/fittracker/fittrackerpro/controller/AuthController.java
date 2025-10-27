package com.fittracker.fittrackerpro.controller; // Use seu pacote completo

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.fittracker.fittrackerpro.dto.LoginRequestDTO;
import com.fittracker.fittrackerpro.dto.UsuarioCadastroDTO;
import com.fittracker.fittrackerpro.model.Usuario;
import com.fittracker.fittrackerpro.service.AuthService;
import com.fittracker.fittrackerpro.service.JwtService; // Import necessário

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // INJEÇÕES (Usando @Autowired para evitar ciclos)
    @Autowired private AuthService authService;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtService jwtService; // JwtService é injetado

    // Rota de Cadastro: POST /auth/register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UsuarioCadastroDTO requestDTO) {
        try {
            Usuario novoUsuario = authService.registrarNovoUsuario(requestDTO);
            return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Rota de Login: POST /auth/login (RETORNANDO O TOKEN JWT)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDTO requestDTO) { // Retorna String (Token)
        try {
            // 1. AUTENTICAÇÃO (Verifica a senha)
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    requestDTO.getEmail(),
                    requestDTO.getSenha()
                )
            );

            // 2. BUSCA USERDETAILS (Se autenticação OK)
            final UserDetails userDetails = authService.loadUserByUsername(requestDTO.getEmail());

            // 3. GERAÇÃO DO TOKEN JWT (Usando o JwtService)
            final String jwtToken = jwtService.generateToken(userDetails);

            // 4. RETORNA O TOKEN
            return ResponseEntity.ok(jwtToken); // Retorna a string do Token JWT

        } catch (AuthenticationException e) {
            // Se a autenticação falhar (senha errada, etc.)
            return new ResponseEntity<>("Credenciais Inválidas", HttpStatus.UNAUTHORIZED);
        }
    }
}