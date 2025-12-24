package com.fittracker.fittrackerpro.dto.auth.request;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDTO(
        @NotBlank(message = "O nome não pode estar em branco")
        String nome,

        @NotBlank(message = "O email é obrigatório")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        String senha

) {}
