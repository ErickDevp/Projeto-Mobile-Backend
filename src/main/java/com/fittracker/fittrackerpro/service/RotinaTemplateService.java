package com.fittracker.fittrackerpro.service;

import com.fittracker.fittrackerpro.dto.diaRotina.DiaRequestDTO;
import com.fittracker.fittrackerpro.dto.rotinaTemplate.request.RotinaTemplateRequestDTO;
import com.fittracker.fittrackerpro.dto.rotinaTemplate.response.RotinaTemplateResponseDTO;
import com.fittracker.fittrackerpro.dto.rotinaTemplate.response.RotinaTemplateTodosResponseDTO;
import com.fittracker.fittrackerpro.entity.DiaRotina;
import com.fittracker.fittrackerpro.entity.RotinaTemplate;
import com.fittracker.fittrackerpro.entity.Treino;
import com.fittracker.fittrackerpro.mapper.RotinaTemplateMapper;
import com.fittracker.fittrackerpro.repository.RotinaTemplateRepository;
import com.fittracker.fittrackerpro.repository.UsuarioRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RotinaTemplateService {

    public final RotinaTemplateRepository repository;
    public final UsuarioRepository usuarioRepository;
    public final RotinaTemplateMapper rotinaTemplateMapper;
    private final TreinoService treinoService;

    public RotinaTemplateService(RotinaTemplateRepository repository, UsuarioRepository usuarioRepository, RotinaTemplateMapper rotinaTemplateMapper, TreinoService treinoService) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.rotinaTemplateMapper = rotinaTemplateMapper;
        this.treinoService = treinoService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public RotinaTemplateResponseDTO buscarTemplateRotinaPorId(Long id, String emailLogado) {
        var usuario = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        RotinaTemplate template = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rotina não encontrado"));

        return rotinaTemplateMapper.toResponseDTO(template);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<RotinaTemplateTodosResponseDTO> buscarTodosTemplateRotina(String emailLogado) {
        var usuario = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return repository.findAll()
                .stream()
                .map(rotinaTemplateMapper::toResponseTodosDTO)
                .toList();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public RotinaTemplateResponseDTO criarTemplateRotina(RotinaTemplateRequestDTO dto, String emailLogado) {
        var usuario = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        RotinaTemplate template = RotinaTemplate.builder()
                .nome(dto.nome())
                .nivel(dto.nivel())
                .objetivo(dto.objetivo())
                .descricao(dto.descricao())
                .build();

        for (DiaRequestDTO diaDTO : dto.dias()) {
            Treino treino = treinoService.criarTreinoEntity(
                    diaDTO.treino(),
                    emailLogado
            );

            DiaRotina dia = new DiaRotina();
            dia.setDiaSemana(diaDTO.dia());
            dia.setTreino(treino);

            template.adicionarDia(dia);
        }

        repository.save(template);
        return rotinaTemplateMapper.toResponseDTO(template);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public void apagarTemplateRotina(Long id, String emailLogado) {
        RotinaTemplate template = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template Rotina não encontrado"));

        var usuario = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        repository.delete(template);
    }

}
