package com.fittracker.fittrackerpro.dto.rotinaTemplate.response;

import com.fittracker.fittrackerpro.dto.diaRotina.DiaResponseDTO;
import com.fittracker.fittrackerpro.entity.enums.NivelTreino;
import com.fittracker.fittrackerpro.entity.enums.ObjetivoUsuario;

import java.util.List;

public record RotinaTemplateResponseDTO(
        Long id,
        String nome,
        String descricao,
        ObjetivoUsuario objetivo,
        NivelTreino nivel,
        List<DiaResponseDTO> dias
){}
