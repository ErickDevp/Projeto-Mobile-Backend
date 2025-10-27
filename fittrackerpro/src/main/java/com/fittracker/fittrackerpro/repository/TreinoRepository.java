package com.fittracker.fittrackerpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fittracker.fittrackerpro.model.Treino;
import java.util.List;

public interface TreinoRepository extends JpaRepository<Treino, Long> {

    
    List<Treino> findByUsuarioIdOrderByDataTreinoDesc(Long usuarioId); 
    
}