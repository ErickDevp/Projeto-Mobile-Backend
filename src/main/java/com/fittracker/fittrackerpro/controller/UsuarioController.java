package com.fittracker.fittrackerpro.controller;

import com.fittracker.fittrackerpro.dto.usuario.UsuarioRequestDTO;
import com.fittracker.fittrackerpro.dto.usuario.UsuarioResponseDTO;
import com.fittracker.fittrackerpro.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/usuario/me")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<UsuarioResponseDTO> buscarUsuario(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(usuarioService.buscarUsuario(user.getUsername()));
    }

    @PutMapping
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@RequestBody @Valid UsuarioRequestDTO dto,
                                                               @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(usuarioService.atualizarUsuario(dto, user.getUsername()));
    }

    @DeleteMapping
    public ResponseEntity<Void> apagarUsuario(@AuthenticationPrincipal UserDetails user) {
        usuarioService.apagarUsuario(user.getUsername());
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/foto",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Void> uploadFoto(@RequestParam("foto") MultipartFile foto, @AuthenticationPrincipal UserDetails user) {

        usuarioService.salvarFotoPerfil(foto, user.getUsername());
        return ResponseEntity.ok().build();
    }

}
