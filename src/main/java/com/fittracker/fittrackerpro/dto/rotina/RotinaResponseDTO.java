package com.fittracker.fittrackerpro.dto.rotina;

import com.fittracker.fittrackerpro.dto.diaRotina.DiaResponseDTO;

import java.util.List;

public record RotinaResponseDTO (
        Long id,
        String nome,
        String descricao,
        List<DiaResponseDTO> dias
){}
