package com.fittracker.fittrackerpro.service;

import com.fittracker.fittrackerpro.dto.diaRotina.DiaRequestDTO;
import com.fittracker.fittrackerpro.dto.rotina.RotinaRequestDTO;
import com.fittracker.fittrackerpro.dto.rotina.RotinaResponseDTO;
import com.fittracker.fittrackerpro.entity.*;
import com.fittracker.fittrackerpro.mapper.RotinaMapper;
import com.fittracker.fittrackerpro.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RotinaService {

    private final RotinaRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final RotinaMapper rotinaMapper;
    private final TreinoService treinoService;
    private final RotinaTemplateRepository rotinaTemplateRepository;
    private final TreinoCloneService treinoCloneService;

    public RotinaService(RotinaRepository repository, UsuarioRepository usuarioRepository, RotinaMapper rotinaMapper, TreinoService treinoService, RotinaTemplateRepository rotinaTemplateRepository, TreinoCloneService treinoCloneService) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.rotinaMapper = rotinaMapper;
        this.treinoService = treinoService;
        this.rotinaTemplateRepository = rotinaTemplateRepository;
        this.treinoCloneService = treinoCloneService;
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

        Rotina rotina = Rotina.builder()
                .nome(dto.nome())
                .dataInicio(LocalDate.now())
                .usuario(usuario)
                .build();

        for (DiaRequestDTO diaDTO : dto.dias()) {

            Treino treino = treinoService.criarTreinoEntity(
                    diaDTO.treino(),
                    emailLogado
            );

            DiaRotina dia = new DiaRotina();
            dia.setDiaSemana(diaDTO.dia());

            dia.setTreino(treino);
            treino.setDiaRotina(dia);

            rotina.adicionarDia(dia);
        }

        repository.save(rotina);
        return rotinaMapper.toResponseDTO(rotina);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public RotinaResponseDTO salvaRotinaTemplate(Long id, String emailLogado) {
        var usuario = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        RotinaTemplate template = rotinaTemplateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rotina não encontrado"));

        Rotina rotina = Rotina.builder()
                .nome(template.getNome())
                .dataInicio(LocalDate.now())
                .usuario(usuario)
                .build();

        for (DiaRotina dia : clonarDias(template.getDias(), usuario)) {
            rotina.adicionarDia(dia);
        }

        return rotinaMapper.toResponseDTO(repository.save(rotina));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Transactional
    public void apagarRotina(Long id, String emailLogado) {

        Usuario usuario = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Rotina rotina = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rotina não encontrada"));

        if (!rotina.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Sem permissão");
        }

        repository.delete(rotina);
    }

    public List<DiaRotina> clonarDias(List<DiaRotina> diasTemplate, Usuario usuario) {
        return diasTemplate.stream()
                .map(dia -> clonarDia(dia, usuario))
                .toList();
    }

    private DiaRotina clonarDia(DiaRotina diaTemplate, Usuario usuario) {
        DiaRotina dia = DiaRotina.builder()
                .diaSemana(diaTemplate.getDiaSemana())
                .treino(treinoCloneService.clonarTreino(diaTemplate.getTreino(), usuario))
                .build();

        return dia;
    }
}
