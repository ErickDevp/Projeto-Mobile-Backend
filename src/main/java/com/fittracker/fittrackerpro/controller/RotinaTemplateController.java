package com.fittracker.fittrackerpro.controller;

import com.fittracker.fittrackerpro.dto.rotinaTemplate.request.RotinaTemplateRequestDTO;
import com.fittracker.fittrackerpro.dto.rotinaTemplate.response.RotinaTemplateResponseDTO;
import com.fittracker.fittrackerpro.dto.rotinaTemplate.response.RotinaTemplateTodosResponseDTO;
import com.fittracker.fittrackerpro.service.RotinaTemplateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rotina/explore")
public class RotinaTemplateController {

    public final RotinaTemplateService service;

    public RotinaTemplateController(RotinaTemplateService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RotinaTemplateResponseDTO> buscarTemplateRotinaPorId(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(service.buscarTemplateRotinaPorId(id, user.getUsername()));
    }

    @GetMapping
    public ResponseEntity<List<RotinaTemplateTodosResponseDTO>> buscarTodosTemplateRotina(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(service.buscarTodosTemplateRotina(user.getUsername()));
    }

    @PostMapping("/criar")
    public ResponseEntity<RotinaTemplateResponseDTO> criarTemplateRotina(@RequestBody @Valid RotinaTemplateRequestDTO dto, @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarTemplateRotina(dto, user.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarTemplateRotina(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        service.apagarTemplateRotina(id, user.getUsername());
        return ResponseEntity.noContent().build();
    }
}
