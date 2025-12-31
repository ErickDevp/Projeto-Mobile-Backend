package com.fittracker.fittrackerpro.mapper;

import com.fittracker.fittrackerpro.dto.rotina.RotinaResponseDTO;
import com.fittracker.fittrackerpro.entity.Rotina;
import org.springframework.stereotype.Component;

@Component
public class RotinaMapper {

    public final DiaRotinaMapper diaRotinaMapper;

    public RotinaMapper(DiaRotinaMapper diaRotinaMapper) {
        this.diaRotinaMapper = diaRotinaMapper;
    }

    public RotinaResponseDTO toResponseDTO(Rotina rotina) {

        return new RotinaResponseDTO(
                rotina.getId(),
                rotina.getNome(),
                rotina.getDescricao(),
                diaRotinaMapper.toResponseDTO(rotina.getDias())
        );
    }
}
