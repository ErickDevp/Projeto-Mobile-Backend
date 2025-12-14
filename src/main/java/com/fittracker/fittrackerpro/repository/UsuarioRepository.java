package com.fittracker.fittrackerpro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fittracker.fittrackerpro.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	boolean existsByEmail(String email);
	
	Optional <Usuario> findByEmail(String email);

}
