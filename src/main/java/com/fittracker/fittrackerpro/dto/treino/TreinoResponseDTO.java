package com.fittracker.fittrackerpro.dto.treino;

import com.fittracker.fittrackerpro.dto.diaRotina.DiaMapperTreinoResponseDTO;
import com.fittracker.fittrackerpro.dto.exercicio.ExercicioResponseDTO;

import java.time.LocalDate;
import java.util.List;

public record TreinoResponseDTO(
        Long id,
        DiaMapperTreinoResponseDTO diaRotina,
        String nomeTreino,
        LocalDate dataTreino,
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
