package com.fittracker.fittrackerpro.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PasswordResetRequestDTO(
        @NotBlank(message = "O email é obrigatório")
        @Email
        String email
) {
}
