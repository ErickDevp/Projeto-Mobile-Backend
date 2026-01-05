package com.fittracker.fittrackerpro.dto.rotina;

import com.fittracker.fittrackerpro.dto.diaRotina.DiaResponseDTO;

import java.time.LocalDate;
import java.util.List;

public record RotinaResponseDTO (
        Long id,
        String nome,
        LocalDate dataInicio,
        List<DiaResponseDTO> dias
){}
