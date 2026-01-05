package com.fittracker.fittrackerpro.controller;

import com.fittracker.fittrackerpro.dto.auth.request.PasswordChangeRequestDTO;
import com.fittracker.fittrackerpro.dto.auth.request.PasswordResetRequestDTO;
import com.fittracker.fittrackerpro.dto.auth.request.RegisterRequestDTO;
import com.fittracker.fittrackerpro.dto.auth.response.AuthResponseDTO;
import com.fittracker.fittrackerpro.dto.auth.response.PasswordResetResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.fittracker.fittrackerpro.dto.auth.request.LoginRequestDTO;
import com.fittracker.fittrackerpro.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody @Valid RegisterRequestDTO dto) {
        return ResponseEntity.ok(authService.saveUsuario(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<PasswordResetResponseDTO> forgotPassword(@RequestBody PasswordResetRequestDTO dto) {
        return ResponseEntity.ok(authService.solicitarRedefinicaoSenha(dto));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody PasswordChangeRequestDTO dto) {
        authService.redefinirSenha(dto);
        return ResponseEntity.noContent().build();
    }
}