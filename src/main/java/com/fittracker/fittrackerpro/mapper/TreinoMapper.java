package com.fittracker.fittrackerpro.mapper;

import com.fittracker.fittrackerpro.dto.diaRotina.DiaMapperTreinoResponseDTO;
import com.fittracker.fittrackerpro.dto.exercicio.ExercicioResponseDTO;
import com.fittracker.fittrackerpro.dto.treino.TreinoResponseDTO;
import com.fittracker.fittrackerpro.entity.Treino;
import org.springframework.stereotype.Component;

import java.util.List;

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

        // ✅ NULL-SAFE para DiaRotina
        DiaMapperTreinoResponseDTO diaRotinaDTO =
                treino.getDiaRotina() != null
                        ? new DiaMapperTreinoResponseDTO(treino.getDiaRotina().getId())
                        : null;

        return new TreinoResponseDTO(
                treino.getId(),
                diaRotinaDTO,
                treino.getNomeTreino(),
                treino.getCriado_em(),
                treino.getDuracaoMin(),
                treino.getIntensidadeGeral(),
                treino.getObservacoes(),
                exercicios
        );
    }
}
