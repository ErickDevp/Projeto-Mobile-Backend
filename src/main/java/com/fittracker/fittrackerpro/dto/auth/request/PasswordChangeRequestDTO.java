package com.fittracker.fittrackerpro.dto.auth.request;

import jakarta.validation.constraints.NotBlank;

public record PasswordChangeRequestDTO(
        @NotBlank(message = "O token é obrigatória")
        String token,

        @NotBlank(message = "A senha é obrigatória")
        String novaSenha
) {
}
