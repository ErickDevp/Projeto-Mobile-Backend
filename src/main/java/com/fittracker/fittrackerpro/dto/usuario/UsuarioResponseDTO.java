package com.fittracker.fittrackerpro.dto.usuario;

import com.fittracker.fittrackerpro.entity.enums.ObjetivoUsuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        Integer totalTreino,
        Integer diasAtivo,
        ObjetivoUsuario objetivo
) {}
