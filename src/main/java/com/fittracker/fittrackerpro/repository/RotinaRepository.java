package com.fittracker.fittrackerpro.repository;

import com.fittracker.fittrackerpro.entity.Rotina;
import com.fittracker.fittrackerpro.entity.Treino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RotinaRepository extends JpaRepository<Rotina, Long> {
    List<Rotina> findByUsuarioId(Long id);

}
