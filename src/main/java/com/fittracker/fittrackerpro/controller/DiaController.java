package com.fittracker.fittrackerpro.controller;

import com.fittracker.fittrackerpro.service.DiaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dia")
public class DiaController {

    public final DiaService diaService;

    public DiaController(DiaService diaService) {
        this.diaService = diaService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarDiaDaRotina(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        diaService.apagarDiaDaRotina(id, user.getUsername());
        return ResponseEntity.noContent().build();
    }
}
