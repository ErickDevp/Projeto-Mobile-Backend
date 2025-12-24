package com.fittracker.fittrackerpro.controller; // Use seu pacote completo

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fittracker.fittrackerpro.dto.treino.TreinoRequestDTO;
import com.fittracker.fittrackerpro.dto.treino.TreinoResponseDTO; // NOVO IMPORT
import com.fittracker.fittrackerpro.entity.Treino;
import com.fittracker.fittrackerpro.service.TreinoService;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/treinos") // URL base para treinos
public class TreinoController {

    private final TreinoService treinoService;

    // Construtor para Injeção
    public TreinoController(TreinoService treinoService) {
        this.treinoService = treinoService;
    }


    /*
    // --- ROTA PARA REGISTRAR UM NOVO TREINO ---
    // URL: POST /api/treinos/registrar/{usuarioId}
    @PostMapping("/registrar/{usuarioId}")
    public ResponseEntity<?> registrarTreino(
            @PathVariable Long usuarioId,
            @Valid @RequestBody TreinoRequestDTO treinoDTO
    ) {
        try {
            Treino treinoSalvo = treinoService.registrarNovoTreino(treinoDTO, usuarioId);
            // Poderíamos mapear treinoSalvo para um TreinoResponseDTO aqui também para consistência
            return new ResponseEntity<>(treinoSalvo, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // --- ROTA PARA BUSCAR O HISTÓRICO (Retornando DTOs) ---
    // URL: GET /api/treinos/historico/{usuarioId}
    @GetMapping("/historico/{usuarioId}")
    public ResponseEntity<List<TreinoResponseDTO>> buscarHistorico(@PathVariable Long usuarioId) { // Tipo de retorno mudou
        try {
            // Chama o serviço que agora retorna List<TreinoResponseDTO>
            List<TreinoResponseDTO> historicoDTO = treinoService.buscarHistoricoPorUsuario(usuarioId);
            return ResponseEntity.ok(historicoDTO); // Retorna a lista de DTOs
        } catch (RuntimeException e) {
             // Retorna 400 Bad Request com uma lista vazia ou null no corpo
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

     */
}