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
import com.fittracker.fittrackerpro.service.JwtService; 
import com.fittracker.fittrackerpro.dto.LoginResponseDTO; // <-- Importação do DTO de Resposta

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // INJEÇÕES (Usando @Autowired para evitar ciclos)
    @Autowired private AuthService authService;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtService jwtService; // JwtService é injetado

    // Rota de Cadastro: POST /auth/register (CORRIGIDA)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UsuarioCadastroDTO requestDTO) {
        try {
            // CORREÇÃO AQUI: Passando o passwordEncoder para o service
            Usuario novoUsuario = authService.registrarNovoUsuario(requestDTO, passwordEncoder); 
            return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Rota de Login: POST /auth/login (IMPLEMENTADA)
    // Retorna o LoginResponseDTO (Token e ID do Usuário)
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO requestDTO) { 
        try {
            // 1. AUTENTICAÇÃO (Verifica a senha)
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    requestDTO.getEmail(),
                    requestDTO.getSenha()
                )
            );
            
            // 2. BUSCA O USUÁRIO COMPLETO (para pegar o ID)
            // Fazemos o cast para Usuario para acessar o .getId()
            final Usuario userDetails = (Usuario) authService.loadUserByUsername(requestDTO.getEmail());
            
            // 3. GERAÇÃO DO TOKEN
            final String jwtToken = jwtService.generateToken(userDetails);
            
            // 4. CRIA O DTO DE RESPOSTA
            LoginResponseDTO response = new LoginResponseDTO(jwtToken, userDetails.getId());
            
            // 5. RETORNA O DTO
            return ResponseEntity.ok(response);
            
        } catch (AuthenticationException e) {
            // Se a autenticação falhar (senha errada, etc.)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}