package com.fittracker.fittrackerpro.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fittracker.fittrackerpro.entity.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByEmail(String email);
}
