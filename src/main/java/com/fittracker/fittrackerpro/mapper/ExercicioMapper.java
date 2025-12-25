package com.fittracker.fittrackerpro.mapper;

import com.fittracker.fittrackerpro.dto.exercicio.ExercicioResponseDTO;
import com.fittracker.fittrackerpro.entity.Exercicio;
import org.springframework.stereotype.Component;

@Component
public class ExercicioMapper {

    public ExercicioResponseDTO toResponseDTO(Exercicio exercicio) {

        return new ExercicioResponseDTO(
                exercicio.getId(),
                exercicio.getNomeExercicio(),
                exercicio.getSeries(),
                exercicio.getRepeticoes(),
                exercicio.getCargaTotalKg(),
                exercicio.getObservacoesEx()
        );
    }
}

