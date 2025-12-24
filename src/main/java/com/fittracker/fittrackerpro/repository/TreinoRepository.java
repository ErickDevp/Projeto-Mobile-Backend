package com.fittracker.fittrackerpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fittracker.fittrackerpro.entity.Treino;
import java.util.List;
import java.util.Optional;

public interface TreinoRepository extends JpaRepository<Treino, Long> {
    Optional<Treino> findByIdAndUsuarioEmail(Long id, String email);

}