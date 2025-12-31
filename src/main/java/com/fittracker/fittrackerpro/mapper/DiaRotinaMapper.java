package com.fittracker.fittrackerpro.mapper;

import com.fittracker.fittrackerpro.dto.diaRotina.DiaResponseDTO;
import com.fittracker.fittrackerpro.entity.DiaRotina;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DiaRotinaMapper {

    private final TreinoMapper treinoMapper;

    public DiaRotinaMapper(TreinoMapper treinoMapper) {
        this.treinoMapper = treinoMapper;
    }

    public DiaResponseDTO toResponseDTO(DiaRotina dia) {

        return new DiaResponseDTO(
                dia.getId(),
                dia.getDiaSemana(),
                treinoMapper.toResponseDTO(dia.getTreino())
        );
    }

    public List<DiaResponseDTO> toResponseDTO(List<DiaRotina> dias) {
        return dias.stream()
                .map(this::toResponseDTO)
                .toList();
    }
}
