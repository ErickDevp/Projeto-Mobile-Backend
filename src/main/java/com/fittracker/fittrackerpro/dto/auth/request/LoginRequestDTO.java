package com.fittracker.fittrackerpro.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO (
        @NotBlank(message = "o email é obrigatorio")
        @Email(message = "o formato do meial e invalido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        String senha
){}
