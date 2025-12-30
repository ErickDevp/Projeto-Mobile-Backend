package com.fittracker.fittrackerpro.dto.exercicio;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ExercicioRequestDTO (
        Long treinoId,

        @NotBlank(message = "Nome do exercício não pode ser vazio")
        String nomeExercicio,

        @NotNull(message = "Número de séries é obrigatório")
        @Positive(message = "Número de séries deve ser positivo")
        Integer series,

        @NotNull(message = "Número de repetições é obrigatório")
        String repeticoes,

        @NotNull(message = "Carga é obrigatória (pode ser 0.0)")
        @Positive(message = "Número de carga deve ser positivo")
        Double cargaTotalKg,

        @Size(max = 150)
        String observacoesEx
) {}