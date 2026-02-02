package com.fittracker.fittrackerpro.service;

import com.fittracker.fittrackerpro.dto.treino.TreinoRequestDTO;
import com.fittracker.fittrackerpro.dto.treino.TreinoResponseDTO;
import com.fittracker.fittrackerpro.entity.*;
import com.fittracker.fittrackerpro.mapper.TreinoMapper;
import com.fittracker.fittrackerpro.repository.DiaRepository;
import com.fittracker.fittrackerpro.repository.RotinaRepository;
import com.fittracker.fittrackerpro.repository.TreinoRepository;
import com.fittracker.fittrackerpro.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TreinoService {

    private final TreinoRepository repository;
    private final TreinoMapper treinoMapper;
    private final UsuarioRepository usuarioRepository;
    private final DiaRepository diaRepository;
    private final TreinoCloneService treinoCloneService;

    public TreinoService(TreinoRepository repository, UsuarioRepository usuarioRepository, TreinoMapper treinoMapper, DiaRepository diaRepository, TreinoCloneService treinoCloneService) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.treinoMapper = treinoMapper;
        this.diaRepository = diaRepository;
        this.treinoCloneService = treinoCloneService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public TreinoResponseDTO buscarTreinoPorId(Long id, String emailLogado) {
        var usuario = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Treino treino = repository
                .findByIdAndUsuarioIdAndDiaRotinaIsNull(id, usuario.getId())
                .orElseThrow(() ->
                        new RuntimeException("Treino não encontrado ou é um modelo de rotina"));

        return treinoMapper.toResponseDTO(treino);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<TreinoResponseDTO> buscarTreinos(String emailLogado) {
        Usuario usuario = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return repository.findByUsuarioIdAndDiaRotinaIsNull(usuario.getId())
                .stream()
                .map(treinoMapper::toResponseDTO)
                .toList();

    }

    @Transactional // Importante para garantir que salve tudo junto
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public TreinoResponseDTO criarTreino(TreinoRequestDTO dto, String emailLogado) {
        var usuario = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Treino entity = Treino.builder()
                .usuario(usuario)
                .diaRotina(null)
                .nomeTreino(dto.nomeTreino())
                .intensidadeGeral(dto.intensidadeGeral())
                .duracaoMin(dto.duracaoMin())
                .observacoes(dto.observacoes())
                .build();

        List<Exercicio> exercicios = dto.exercicios().stream()
                .map(exDto -> Exercicio.builder()
                        .nomeExercicio(exDto.nomeExercicio())
                        .series(exDto.series())
                        .repeticoes(exDto.repeticoes())
                        .cargaTotalKg(exDto.cargaTotalKg()) // Double
                        .observacoesEx(exDto.observacoesEx())
                        .treino(entity)
                        .build()
                )
                .toList();

        entity.setExercicios(exercicios);
        usuario.registrarTreino();
        usuario.registrarDiaAtivo();

        usuarioRepository.save(usuario);
        repository.save(entity);

        return treinoMapper.toResponseDTO(entity);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public TreinoResponseDTO atualizarTreino(TreinoRequestDTO dto, Long id, String emailLogado) {
        var treino = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Treino não encontrado"));

        if(!treino.getUsuario().getEmail().equals(emailLogado)) {
            throw new RuntimeException("Você não pode alterar treino de outro usuário");
        }

        if(dto.nomeTreino() != null) treino.setNomeTreino(dto.nomeTreino());
        if(dto.intensidadeGeral() != null) treino.setIntensidadeGeral(dto.intensidadeGeral());
        if(dto.duracaoMin() != null) treino.setDuracaoMin(dto.duracaoMin());
        if(dto.observacoes() != null) treino.setObservacoes(dto.observacoes());

        if (dto.exercicios() != null) {
            treino.getExercicios().clear();

            List<Exercicio> novosExercicios = dto.exercicios().stream()
                    .map(exDto -> Exercicio.builder()
                            .nomeExercicio(exDto.nomeExercicio())
                            .series(exDto.series())
                            .repeticoes(exDto.repeticoes())
                            .cargaTotalKg(exDto.cargaTotalKg())
                            .observacoesEx(exDto.observacoesEx())
                            .treino(treino)
                            .build())
                    .collect(Collectors.toList());


            treino.getExercicios().addAll(novosExercicios);
        }

        Treino treinoSalvo = repository.saveAndFlush(treino);

        return treinoMapper.toResponseDTO(treinoSalvo);
    }

    @Transactional
    public Treino criarTreinoEntity(TreinoRequestDTO dto, String emailLogado) {
        var usuario = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Treino entity = Treino.builder()
                .usuario(usuario)
                .nomeTreino(dto.nomeTreino())
                .intensidadeGeral(dto.intensidadeGeral())
                .duracaoMin(dto.duracaoMin())
                .observacoes(dto.observacoes())
                .build();

        List<Exercicio> exercicios = dto.exercicios().stream()
                .map(exDto -> Exercicio.builder()
                        .nomeExercicio(exDto.nomeExercicio())
                        .series(exDto.series())
                        .repeticoes(exDto.repeticoes())
                        .cargaTotalKg(exDto.cargaTotalKg())
                        .observacoesEx(exDto.observacoesEx())
                        .treino(entity)
                        .build()
                )
                .toList();

        entity.setExercicios(exercicios);

        return entity;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public TreinoResponseDTO usarTreinoRotina(Long id, String emailLogado) {
        var usuario = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        DiaRotina dia = diaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dia não encontrado"));

        if (dia.getRotina() == null
                || dia.getRotina().getUsuario() == null
                || !dia.getRotina().getUsuario().equals(usuario)) {
            throw new RuntimeException("Você não pode usar um treino de outro usuário");
        }

        Treino treino = treinoCloneService.clonarTreino(dia.getTreino(), usuario);

        return treinoMapper.toResponseDTO(repository.save(treino));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public void apagarTreino(Long id, String emailLogado) {
        // 1. Busca o Treino
        Treino treino = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Treino não encontrado"));

        // 2. CORREÇÃO DE SEGURANÇA: Verifica se o dono do treino é quem está tentando apagar
        if (!treino.getUsuario().getEmail().equals(emailLogado)) {
            throw new RuntimeException("Você não tem permissão para excluir este treino");
        }

        var usuario = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.removerTreino();
        // Nota: Verificar se 'registrarDiaAtivo' faz sentido ao remover. Geralmente removemos apenas o contador de treinos.
        // Se a lógica for 'fez uma ação no app', ok. Se for 'fez exercicio', talvez não deva chamar aqui.
        // usuario.registrarDiaAtivo();

        usuarioRepository.save(usuario);

        repository.deleteById(id);
    }
}