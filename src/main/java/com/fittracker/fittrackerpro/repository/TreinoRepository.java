package com.fittracker.fittrackerpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fittracker.fittrackerpro.entity.Treino;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TreinoRepository extends JpaRepository<Treino, Long> {
    List<Treino> findByUsuarioId(Long id);
    Optional<Treino> findByIdAndUsuarioEmail(Long id, String email);
    List<Treino> findByUsuarioIdAndDiaRotinaIsNull(Long usuarioId);
    Optional<Treino> findByIdAndUsuarioIdAndDiaRotinaIsNull(
            Long id,
            Long usuarioId
    );
}