package com.fittracker.fittrackerpro.service;

import com.fittracker.fittrackerpro.dto.diaRotina.DiaRequestDTO;
import com.fittracker.fittrackerpro.dto.rotina.RotinaRequestDTO;
import com.fittracker.fittrackerpro.dto.rotina.RotinaResponseDTO;
import com.fittracker.fittrackerpro.entity.DiaRotina;
import com.fittracker.fittrackerpro.entity.Rotina;
import com.fittracker.fittrackerpro.entity.Treino;
import com.fittracker.fittrackerpro.mapper.RotinaMapper;
import com.fittracker.fittrackerpro.repository.RotinaRepository;
import com.fittracker.fittrackerpro.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RotinaService {

    private final RotinaRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final RotinaMapper rotinaMapper;
    private final TreinoService treinoService;

    public RotinaService(RotinaRepository repository, UsuarioRepository usuarioRepository, RotinaMapper rotinaMapper, TreinoService treinoService) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.rotinaMapper = rotinaMapper;
        this.treinoService = treinoService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<RotinaResponseDTO> buscarTodasAsRotinasUsuario(String emailLogado) {
        var usuario = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return repository.findByUsuarioId(usuario.getId())
                .stream()
                .map(rotinaMapper::toResponseDTO)
                .toList();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public RotinaResponseDTO buscarRotinaPorId(Long id, String emailLogado) {
        var usuario = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Rotina rotina = repository.findById(id)
                .filter(r -> r.getUsuario().getId().equals(usuario.getId()))
                .orElseThrow(() -> new RuntimeException("Rotina não encontrado"));

        return rotinaMapper.toResponseDTO(rotina);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Transactional
    public RotinaResponseDTO criarRotina(RotinaRequestDTO dto, String emailLogado) {
        var usuario = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Rotina rotina = new Rotina();
        rotina.setNome(dto.nome());
        rotina.setUsuario(usuario);

        for (DiaRequestDTO diaDTO : dto.dias()) {
            Treino treino = treinoService.criarTreinoEntity(
                    diaDTO.treino(),
                    emailLogado
            );

            DiaRotina dia = new DiaRotina();
            dia.setDiaSemana(diaDTO.dia());
            dia.setTreino(treino);

            rotina.adicionarDia(dia);
        }

        repository.save(rotina);
        return rotinaMapper.toResponseDTO(rotina);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public void apagarRotina(Long id, String emailLogado) {
        var rotina = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rotina não encontrado"));

        if(!rotina.getUsuario().getEmail().equals(emailLogado)) {
            throw new RuntimeException("Você não pode alterar rotina de outro usuário");
        }

        repository.deleteById(id);
    }
}
