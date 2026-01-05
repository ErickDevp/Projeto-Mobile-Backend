package com.fittracker.fittrackerpro.dto.rotina;

import com.fittracker.fittrackerpro.dto.diaRotina.DiaRequestDTO;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record RotinaRequestDTO (
        @Size(max = 50)
        String nome,

        @Size(max = 150)
        String descricao,

        LocalDate dataInicio,

        List<DiaRequestDTO> dias
) {}
