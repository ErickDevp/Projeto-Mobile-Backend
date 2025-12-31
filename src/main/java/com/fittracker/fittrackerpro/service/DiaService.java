package com.fittracker.fittrackerpro.service;

import com.fittracker.fittrackerpro.dto.diaRotina.DiaRequestDTO;
import com.fittracker.fittrackerpro.dto.diaRotina.DiaResponseDTO;
import com.fittracker.fittrackerpro.entity.DiaRotina;
import com.fittracker.fittrackerpro.mapper.DiaRotinaMapper;
import com.fittracker.fittrackerpro.mapper.RotinaMapper;
import com.fittracker.fittrackerpro.mapper.TreinoMapper;
import com.fittracker.fittrackerpro.repository.DiaRepository;
import com.fittracker.fittrackerpro.repository.RotinaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class DiaService {

    public final DiaRepository repository;
    public final RotinaRepository rotinaRepository;
    public final DiaRotinaMapper diaRotinaMapper;
    public final TreinoMapper treinoMapper;

    public DiaService(DiaRepository repository, RotinaRepository rotinaRepository, DiaRotinaMapper diaRotinaMapper, TreinoMapper treinoMapper) {
        this.repository = repository;
        this.rotinaRepository = rotinaRepository;
        this.diaRotinaMapper = diaRotinaMapper;
        this.treinoMapper = treinoMapper;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public void apagarDiaDaRotina(Long id, String emailLogado) {
        DiaRotina dia = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("dia não encontrado na Rotina"));

        if(!dia.getRotina().getUsuario().getEmail().equals(emailLogado)) {
            throw new RuntimeException("Você não pode alterar rotina de outro usuário");
        }

        repository.deleteById(id);
    }
}
