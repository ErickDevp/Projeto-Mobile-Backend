package com.fittracker.fittrackerpro.repository; 

import org.springframework.data.jpa.repository.JpaRepository;
import com.fittracker.fittrackerpro.model.Exercicio;

public interface ExercicioRepository extends JpaRepository<Exercicio, Long> {

}