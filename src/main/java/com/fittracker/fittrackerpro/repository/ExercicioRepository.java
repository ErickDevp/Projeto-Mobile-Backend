package com.fittracker.fittrackerpro.repository; 

import com.fittracker.fittrackerpro.dto.exercicio.ExercicioResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fittracker.fittrackerpro.entity.Exercicio;

import java.util.List;

public interface ExercicioRepository extends JpaRepository<Exercicio, Long> {
    List<ExercicioResponseDTO> findByTreinoId(Long id);
}