package com.fittracker.fittrackerpro.dto.rotina;

import com.fittracker.fittrackerpro.dto.diaRotina.DiaRequestDTO;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RotinaRequestDTO (
        @Size(max = 50)
        String nome,

        @Size(max = 150)
        String descricao,

        List<DiaRequestDTO> dias
) {}
