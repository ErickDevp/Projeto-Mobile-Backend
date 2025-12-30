package com.fittracker.fittrackerpro.dto.treino;

import com.fittracker.fittrackerpro.dto.exercicio.ExercicioRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record TreinoRequestDTO(

        @Size(max = 50)
        String nomeRotina,
        Integer duracaoMin,
        Integer intensidadeGeral,

        @Size(max = 150)
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
