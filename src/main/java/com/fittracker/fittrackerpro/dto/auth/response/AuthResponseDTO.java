package com.fittracker.fittrackerpro.dto.auth.response;

public record AuthResponseDTO(
        String token,
        Long id,
        String nome
) {}

