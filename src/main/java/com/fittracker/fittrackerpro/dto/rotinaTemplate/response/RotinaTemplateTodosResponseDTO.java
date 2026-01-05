package com.fittracker.fittrackerpro.dto.rotinaTemplate.response;

import com.fittracker.fittrackerpro.entity.enums.NivelTreino;
import com.fittracker.fittrackerpro.entity.enums.ObjetivoUsuario;

public record RotinaTemplateTodosResponseDTO(
        Long id,
        String nome,
        String descricao,
        ObjetivoUsuario objetivo,
        NivelTreino nivel
){}
