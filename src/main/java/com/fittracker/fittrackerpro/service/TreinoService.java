package com.fittracker.fittrackerpro.service; // Use seu pacote completo

import com.fittracker.fittrackerpro.repository.TreinoRepository;
import org.springframework.stereotype.Service;

@Service
public class TreinoService {

    private final TreinoRepository repository;

    public TreinoService(TreinoRepository repository) {
        this.repository = repository;
    }


}