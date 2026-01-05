package com.fittracker.fittrackerpro.repository;

import com.fittracker.fittrackerpro.entity.Rotina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RotinaRepository extends JpaRepository<Rotina, Long> {
    List<Rotina> findByUsuarioId(Long id);

}
