package com.fittracker.fittrackerpro.dto.rotinaTemplate.response;

import com.fittracker.fittrackerpro.dto.diaRotina.DiaRequestDTO;
import com.fittracker.fittrackerpro.dto.diaRotina.DiaRotinaTemplateResponseDTO;
import com.fittracker.fittrackerpro.entity.enums.NivelTreino;
import com.fittracker.fittrackerpro.entity.enums.ObjetivoUsuario;

import java.util.List;

public record RotinaTemplateTodosResponseDTO(
        Long id,
        String nome,
        String descricao,
        ObjetivoUsuario objetivo,
        NivelTreino nivel,
        List<DiaRotinaTemplateResponseDTO> dias
){}
