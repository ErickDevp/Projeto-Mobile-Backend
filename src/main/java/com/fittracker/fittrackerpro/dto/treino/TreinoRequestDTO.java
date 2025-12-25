package com.fittracker.fittrackerpro.dto.treino;

import com.fittracker.fittrackerpro.dto.exercicio.ExercicioRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record TreinoRequestDTO(
        String nomeRotina,
        Integer duracaoMin,
        Integer intensidadeGeral,
        String observacoes,

        @NotEmpty(message = "A lista de exercícios não pode ser vazia")
        @Valid
        List<ExercicioRequestDTO> exercicios
) {
    public TreinoRequestDTO {
        if (exercicios == null) {
            throw new IllegalArgumentException("A lista de exercícios é obrigatória");
        }
    }
}
