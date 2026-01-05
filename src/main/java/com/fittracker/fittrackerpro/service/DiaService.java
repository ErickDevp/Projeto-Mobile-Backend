package com.fittracker.fittrackerpro.service;

import com.fittracker.fittrackerpro.entity.DiaRotina;
import com.fittracker.fittrackerpro.repository.DiaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class DiaService {

    public final DiaRepository repository;

    public DiaService(DiaRepository repository) {
        this.repository = repository;
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
