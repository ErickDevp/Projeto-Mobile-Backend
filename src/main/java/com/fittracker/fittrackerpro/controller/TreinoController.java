package com.fittracker.fittrackerpro.controller;

import com.fittracker.fittrackerpro.dto.treino.TreinoRequestDTO;
import com.fittracker.fittrackerpro.dto.treino.TreinoResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.fittracker.fittrackerpro.service.TreinoService;

import java.util.List;

@RestController
@RequestMapping("/api/treino")
public class TreinoController {

    private final TreinoService treinoService;

    public TreinoController(TreinoService treinoService) {
        this.treinoService = treinoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreinoResponseDTO> buscarTreinoPorId(@PathVariable Long id,
                                                    @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(treinoService.buscarTreinoPorId(id, user.getUsername()));
    }

    @GetMapping
    public ResponseEntity<List<TreinoResponseDTO>> buscarTreinos(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(treinoService.buscarTreinos(user.getUsername()));
    }

    @PostMapping("/criar")
    public ResponseEntity<TreinoResponseDTO> criarTreino(@RequestBody @Valid TreinoRequestDTO dto, @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(treinoService.criarTreino(dto, user.getUsername()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TreinoResponseDTO> atualizarTreino(@RequestBody @Valid TreinoRequestDTO dto,
                                                             @PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(treinoService.atualizarTreino(dto, id, user.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarTreino(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        treinoService.apagarTreino(id, user.getUsername());
        return ResponseEntity.noContent().build();
    }
}