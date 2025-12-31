package com.fittracker.fittrackerpro.dto.diaRotina;

import com.fittracker.fittrackerpro.dto.treino.TreinoRequestDTO;
import com.fittracker.fittrackerpro.entity.enums.DiaSemana;

public record DiaRequestDTO (
        Long id,
        DiaSemana dia,
        TreinoRequestDTO treino
) {}
