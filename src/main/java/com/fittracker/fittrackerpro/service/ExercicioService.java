package com.fittracker.fittrackerpro.service;

import com.fittracker.fittrackerpro.dto.exercicio.ExercicioRequestDTO;
import com.fittracker.fittrackerpro.dto.exercicio.ExercicioResponseDTO;
import com.fittracker.fittrackerpro.entity.Exercicio;
import com.fittracker.fittrackerpro.repository.ExercicioRepository;
import com.fittracker.fittrackerpro.repository.TreinoRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExercicioService {

    private final ExercicioRepository repository;
    private final TreinoRepository treinoRepository;

    public ExercicioService(ExercicioRepository repository, TreinoRepository treinoRepository) {
        this.repository = repository;
        this.treinoRepository = treinoRepository;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<ExercicioResponseDTO> buscarExerciciosDeUmTreino(Long treinoId, String emailLogado) {
        var treino = treinoRepository
                .findByIdAndUsuarioEmail(treinoId, emailLogado)
                .orElseThrow(() -> new RuntimeException("Treino não encontrada para este usuário"));

        return repository.findByTreinoId(treino.getId());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Long criarExercicio(ExercicioRequestDTO dto, Long treinoId, String emailLogado) {
        var treino = treinoRepository
                .findByIdAndUsuarioEmail(treinoId, emailLogado)
                .orElseThrow(() -> new RuntimeException("Treino não encontrada para este usuário"));

        Exercicio entity = Exercicio.builder()
                .treino(treino)
                .nomeExercicio(dto.nomeExercicio())
                .series(dto.series())
                .repeticoes(dto.repeticoes())
                .cargaTotalKg(dto.cargaTotalKg())
                .observacoesEx(dto.observacoesEx())
                .build();

        return repository.save(entity).getId();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public void atualizarExercicio(ExercicioRequestDTO dto, Long id, String emailLogado) {
        var exercicio = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercicio não encontrado"));

        if(!exercicio.getTreino().getUsuario().getEmail().equals(emailLogado)) {
            throw new RuntimeException("Você não pode alterar exercicio de outro usuário");
        }

        if(dto.nomeExercicio() != null) exercicio.setNomeExercicio(dto.nomeExercicio());
        if(dto.series() != null) exercicio.setSeries(dto.series());
        if(dto.repeticoes() != null) exercicio.setRepeticoes(dto.repeticoes());
        if(dto.cargaTotalKg() != null) exercicio.setCargaTotalKg(dto.cargaTotalKg());
        if(dto.observacoesEx() != null) exercicio.setObservacoesEx(dto.observacoesEx());

        if(dto.treinoId() != null) {
            var novoTreino = treinoRepository
                    .findByIdAndUsuarioEmail(dto.treinoId(), emailLogado)
                    .orElseThrow(() -> new RuntimeException("Treino não encontrada para este usuário"));

            exercicio.setTreino(novoTreino);
        }

        repository.save(exercicio);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public void apagarExercicio(Long id, String emailLogado) {
        var exercicio = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercicio não encontrado"));

        if(!exercicio.getTreino().getUsuario().getEmail().equals(emailLogado)) {
            throw new RuntimeException("Você não pode alterar exercicio de outro usuário");
        }

        repository.deleteById(id);
    }
}
