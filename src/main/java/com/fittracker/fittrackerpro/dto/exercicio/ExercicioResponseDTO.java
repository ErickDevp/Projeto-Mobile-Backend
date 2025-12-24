package com.fittracker.fittrackerpro.dto.exercicio;

public record ExercicioResponseDTO (
        Long id,
        String nomeExercicio,
        Integer series,
        String repeticoes,
        Double cargaTotalKg,
        String observacoesEx
) {}