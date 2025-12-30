package com.fittracker.fittrackerpro.service;

import com.fittracker.fittrackerpro.dto.usuario.UsuarioRequestDTO;
import com.fittracker.fittrackerpro.dto.usuario.UsuarioResponseDTO;
import com.fittracker.fittrackerpro.entity.Usuario;
import com.fittracker.fittrackerpro.mapper.UsuarioMapper;
import com.fittracker.fittrackerpro.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper usuarioMapper;
    private final String PASTA_UPLOAD = "uploads/perfil/";


    public UsuarioService(UsuarioRepository repository, UsuarioMapper usuarioMapper) {
        this.repository = repository;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioResponseDTO buscarUsuario(String emailLogado) {
        var usuario = repository.findByEmail(emailLogado)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        return usuarioMapper.toResponseDTO(usuario);
    }

    public UsuarioResponseDTO atualizarUsuario(UsuarioRequestDTO dto, String emailLogado) {
        var usuario = repository.findByEmail(emailLogado)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if(dto.nome() != null) usuario.setNome(dto.nome());
        if(dto.email() != null) usuario.setEmail(dto.email());
        if(dto.objetivo() != null) usuario.setObjetivo(dto.objetivo());

        return usuarioMapper.toResponseDTO(repository.save(usuario));
    }


    public void salvarFotoPerfil(MultipartFile foto, String emailLogado) {
        var usuario = repository.findByEmail(emailLogado)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (foto.getSize() > 2_000_000) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Imagem muito grande (máx 2MB)"
            );
        }

        try {
            Files.createDirectories(Paths.get(PASTA_UPLOAD));

            String nomeArquivo = "usuario_" + usuario.getId() + "_" + foto.getOriginalFilename();
            Path caminho = Paths.get(PASTA_UPLOAD + nomeArquivo);

            Files.write(caminho, foto.getBytes());

            usuario.setFotoPerfil("/uploads/perfil/" + nomeArquivo);
            repository.save(usuario);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar imagem");
        }
    }

    public void apagarUsuario(String emailLogado) {
        var usuario = repository.findByEmail(emailLogado)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        repository.delete(usuario);
    }
}
