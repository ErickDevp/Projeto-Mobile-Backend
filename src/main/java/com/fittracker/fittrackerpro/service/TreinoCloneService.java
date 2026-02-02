package com.fittracker.fittrackerpro.service;

import com.fittracker.fittrackerpro.entity.Exercicio;
import com.fittracker.fittrackerpro.entity.Treino;
import com.fittracker.fittrackerpro.entity.Usuario;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TreinoCloneService {

    public Treino clonarTreino(Treino treinoTemplate, Usuario usuario) {
        Treino treino = Treino.builder()
                .diaRotina(treinoTemplate.getDiaRotina())
                .nomeTreino(treinoTemplate.getNomeTreino())
                .criado_em(LocalDate.now())
                .intensidadeGeral(treinoTemplate.getIntensidadeGeral())
                .duracaoMin(treinoTemplate.getDuracaoMin())
                .observacoes(treinoTemplate.getObservacoes())
                .usuario(usuario)
                .build();

        List<Exercicio> exercicios =
                treinoTemplate.getExercicios()
                        .stream()
                        .map(ex -> clonarExercicio(ex, treino))
                        .toList();

        treino.setExercicios(exercicios);

        return treino;
    }

    public Exercicio clonarExercicio(Exercicio exercicioTemplate, Treino treino) {
        return Exercicio.builder()
                .treino(treino)
                .nomeExercicio(exercicioTemplate.getNomeExercicio())
                .series(exercicioTemplate.getSeries())
                .repeticoes(exercicioTemplate.getRepeticoes())
                .cargaTotalKg(exercicioTemplate.getCargaTotalKg())
                .observacoesEx(exercicioTemplate.getObservacoesEx())
                .build();
    }

}
