package com.fittracker.fittrackerpro.service;

import com.fittracker.fittrackerpro.dto.exercicio.ExercicioRequestDTO;
import com.fittracker.fittrackerpro.dto.exercicio.ExercicioResponseDTO;
import com.fittracker.fittrackerpro.mapper.ExercicioMapper;
import com.fittracker.fittrackerpro.repository.ExercicioRepository;
import com.fittracker.fittrackerpro.repository.TreinoRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExercicioService {

    private final ExercicioRepository repository;
    private final TreinoRepository treinoRepository;
    private final ExercicioMapper exercicioMapper;

    public ExercicioService(ExercicioRepository repository, TreinoRepository treinoRepository, ExercicioMapper exercicioMapper) {
        this.repository = repository;
        this.treinoRepository = treinoRepository;
        this.exercicioMapper = exercicioMapper;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<ExercicioResponseDTO> buscarExerciciosDeUmTreino(Long treinoId, String emailLogado) {
        var treino = treinoRepository
                .findByIdAndUsuarioEmail(treinoId, emailLogado)
                .orElseThrow(() -> new RuntimeException("Treino não encontrada para este usuário"));

        return repository.findByTreinoId(treino.getId());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ExercicioResponseDTO atualizarExercicio(ExercicioRequestDTO dto, Long id, String emailLogado) {
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

        return exercicioMapper.toResponseDTO(repository.save(exercicio));
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
