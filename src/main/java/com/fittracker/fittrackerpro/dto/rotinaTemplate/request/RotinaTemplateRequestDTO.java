package com.fittracker.fittrackerpro.dto.rotinaTemplate.request;

import com.fittracker.fittrackerpro.dto.diaRotina.DiaRequestDTO;
import com.fittracker.fittrackerpro.entity.enums.NivelTreino;
import com.fittracker.fittrackerpro.entity.enums.ObjetivoUsuario;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RotinaTemplateRequestDTO(
        @Size(max = 50)
        String nome,

        @Size(max = 150)
        String descricao,

        ObjetivoUsuario objetivo,
        NivelTreino nivel,

        List<DiaRequestDTO> dias
) {}
