package com.fittracker.fittrackerpro.controller;

import com.fittracker.fittrackerpro.dto.rotina.RotinaRequestDTO;
import com.fittracker.fittrackerpro.dto.rotina.RotinaResponseDTO;
import com.fittracker.fittrackerpro.service.RotinaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rotina")
public class RotinaController {

    public final RotinaService rotinaService;

    public RotinaController(RotinaService rotinaService) {
        this.rotinaService = rotinaService;
    }

    @GetMapping
    public ResponseEntity<List<RotinaResponseDTO>> buscarTodasAsRotinasUsuario(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(rotinaService.buscarTodasAsRotinasUsuario(user.getUsername()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RotinaResponseDTO> buscarRotinaPorId(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(rotinaService.buscarRotinaPorId(id, user.getUsername()));
    }

    @PostMapping("/criar")
    public ResponseEntity<RotinaResponseDTO> criarRotina(@RequestBody @Valid RotinaRequestDTO dto, @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rotinaService.criarRotina(dto, user.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarRotina(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        rotinaService.apagarRotina(id, user.getUsername());
        return ResponseEntity.noContent().build();
    }
}
