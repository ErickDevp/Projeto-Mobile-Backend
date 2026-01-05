package com.fittracker.fittrackerpro.mapper;

import com.fittracker.fittrackerpro.dto.rotinaTemplate.response.RotinaTemplateResponseDTO;
import com.fittracker.fittrackerpro.dto.rotinaTemplate.response.RotinaTemplateTodosResponseDTO;
import com.fittracker.fittrackerpro.entity.RotinaTemplate;
import org.springframework.stereotype.Component;

@Component
public class RotinaTemplateMapper {

    public final DiaRotinaMapper diaRotinaMapper;

    public RotinaTemplateMapper(DiaRotinaMapper diaRotinaMapper) {
        this.diaRotinaMapper = diaRotinaMapper;
    }

    public RotinaTemplateResponseDTO toResponseDTO(RotinaTemplate rotina) {

        return new RotinaTemplateResponseDTO(
                rotina.getId(),
                rotina.getNome(),
                rotina.getDescricao(),
                rotina.getObjetivo(),
                rotina.getNivel(),
                diaRotinaMapper.toResponseDTO(rotina.getDias())
        );
    }

    public RotinaTemplateTodosResponseDTO toResponseTodosDTO(RotinaTemplate rotina) {

        return new RotinaTemplateTodosResponseDTO(
                rotina.getId(),
                rotina.getNome(),
                rotina.getDescricao(),
                rotina.getObjetivo(),
                rotina.getNivel()
        );
    }
}
