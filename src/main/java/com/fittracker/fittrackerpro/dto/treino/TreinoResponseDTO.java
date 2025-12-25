package com.fittracker.fittrackerpro.dto.treino;

import com.fittracker.fittrackerpro.dto.exercicio.ExercicioResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public record TreinoResponseDTO(
        Long id,
        String nomeRotina,
        LocalDateTime dataTreino,
        Integer duracaoMin,
        Integer intensidadeGeral,
        String observacoes,
        List<ExercicioResponseDTO> exercicios
) {
    public TreinoResponseDTO {
        if (exercicios == null) {
            exercicios = List.of(); // lista vazia segura
        }
    }
}
