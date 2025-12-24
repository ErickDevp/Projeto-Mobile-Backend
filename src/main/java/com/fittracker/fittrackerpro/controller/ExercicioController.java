package com.fittracker.fittrackerpro.controller;

import com.fittracker.fittrackerpro.dto.exercicio.ExercicioRequestDTO;
import com.fittracker.fittrackerpro.dto.exercicio.ExercicioResponseDTO;
import com.fittracker.fittrackerpro.service.ExercicioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/treinos/{treinoId}/exercicios")
public class ExercicioController {

    private final ExercicioService exercicioService;

    public ExercicioController(ExercicioService exercicioService) {
        this.exercicioService = exercicioService;
    }

    @GetMapping
    public ResponseEntity<List<ExercicioResponseDTO>> buscarExercicios(@PathVariable Long treinoId,
                                                          @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(exercicioService.buscarExerciciosDeUmTreino(treinoId, user.getUsername()));
    }

    @PostMapping
    public ResponseEntity<Long> criarExercicio(@RequestBody @Valid ExercicioRequestDTO dto,
                                               @PathVariable Long treinoId,
                                               @AuthenticationPrincipal UserDetails user  ) {
        return ResponseEntity.ok(exercicioService.criarExercicio(dto, treinoId, user.getUsername()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarExercicio(@RequestBody @Valid ExercicioRequestDTO dto,
                                                   @PathVariable Long id,
                                                   @AuthenticationPrincipal UserDetails user) {
        exercicioService.atualizarExercicio(dto, id, user.getUsername());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarExercicio(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        exercicioService.apagarExercicio(id, user.getUsername());
        return ResponseEntity.noContent().build();

    }

}
