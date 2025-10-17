package com.fittracker.fittrackerpro.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fittracker.fittrackerpro.dto.UsuarioCadastroDTO;
import com.fittracker.fittrackerpro.model.Usuario;
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
    public ResponseEntity<?> register(@RequestBody @Valid UsuarioCadastroDTO requestDTO) {
        
        try {

            Usuario novoUsuario = authService.registrarNovoUsuario(requestDTO);
            

            return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
            
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}