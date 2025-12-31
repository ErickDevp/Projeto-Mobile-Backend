package com.fittracker.fittrackerpro.dto.diaRotina;

import com.fittracker.fittrackerpro.dto.treino.TreinoResponseDTO;
import com.fittracker.fittrackerpro.entity.enums.DiaSemana;

public record DiaResponseDTO (
        Long id,
        DiaSemana dia,
        TreinoResponseDTO treino
) {}
