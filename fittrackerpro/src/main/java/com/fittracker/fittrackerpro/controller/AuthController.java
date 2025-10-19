package com.fittracker.fittrackerpro.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.fittracker.fittrackerpro.dto.LoginRequestDTO;
import com.fittracker.fittrackerpro.dto.UsuarioCadastroDTO;
import com.fittracker.fittrackerpro.model.Usuario;
import com.fittracker.fittrackerpro.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    
    private final PasswordEncoder passwordEncoder; 

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UsuarioCadastroDTO requestDTO) {
        
        try {
            Usuario novoUsuario = authService.registrarNovoUsuario(requestDTO); 
            return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<Long> login(@RequestBody @Valid LoginRequestDTO requestDTO) { 
        
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    requestDTO.getEmail(),
                    requestDTO.getSenha()
                )
            );
            
            Usuario usuario = (Usuario) authService.loadUserByUsername(requestDTO.getEmail());
            
            return ResponseEntity.ok(usuario.getId());
            
        } catch (AuthenticationException e) {
            return new ResponseEntity("Credenciais Inválidas", HttpStatus.UNAUTHORIZED);
        }
    }
}