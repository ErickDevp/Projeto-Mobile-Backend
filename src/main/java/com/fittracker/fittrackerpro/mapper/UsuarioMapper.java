package com.fittracker.fittrackerpro.mapper;

import com.fittracker.fittrackerpro.dto.usuario.UsuarioResponseDTO;
import com.fittracker.fittrackerpro.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioResponseDTO toResponseDTO(Usuario usuario) {

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTotalTreinos(),
                usuario.getDiasAtivo(),
                usuario.getObjetivo()
        );

    }

}
