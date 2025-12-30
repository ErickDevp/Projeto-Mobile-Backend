package com.fittracker.fittrackerpro.dto.usuario;

import com.fittracker.fittrackerpro.entity.enums.ObjetivoUsuario;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
        String nome,
        String email,
        ObjetivoUsuario objetivo
) {}
