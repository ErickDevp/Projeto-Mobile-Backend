package com.fittracker.fittrackerpro.mapper;

import com.fittracker.fittrackerpro.dto.exercicio.ExercicioRequestDTO;
import com.fittracker.fittrackerpro.dto.exercicio.ExercicioResponseDTO;
import com.fittracker.fittrackerpro.dto.treino.TreinoRequestDTO;
import com.fittracker.fittrackerpro.dto.treino.TreinoResponseDTO;
import com.fittracker.fittrackerpro.entity.Exercicio;
import com.fittracker.fittrackerpro.entity.Treino;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class TreinoMapper {

    public TreinoResponseDTO toResponseDTO(Treino treino) {

        List<ExercicioResponseDTO> exercicios =
                treino.getExercicios() == null
                        ? List.of()
                        : treino.getExercicios().stream()
                        .map(ex -> new ExercicioResponseDTO(
                                ex.getId(),
                                ex.getNomeExercicio(),
                                ex.getSeries(),
                                ex.getRepeticoes(),
                                ex.getCargaTotalKg(),
                                ex.getObservacoesEx()
                        ))
                        .toList();

        return new TreinoResponseDTO(
                treino.getId(),
                treino.getNomeRotina(),
                treino.getCriado_em(),
                treino.getDuracaoMin(),
                treino.getIntensidadeGeral(),
                treino.getObservacoes(),
                exercicios
        );
    }

    public void updateEntityFromDto(TreinoRequestDTO dto, Treino treino) {
        if (dto == null || treino == null) return;

        if (dto.nomeRotina() != null)
            treino.setNomeRotina(dto.nomeRotina());

        if (dto.duracaoMin() != null)
            treino.setDuracaoMin(dto.duracaoMin());

        if (dto.intensidadeGeral() != null)
            treino.setIntensidadeGeral(dto.intensidadeGeral());

        if (dto.observacoes() != null)
            treino.setObservacoes(dto.observacoes());
    }
}

