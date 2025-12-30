package com.fittracker.fittrackerpro.service; // Use seu pacote completo

import com.fittracker.fittrackerpro.dto.treino.TreinoRequestDTO;
import com.fittracker.fittrackerpro.dto.treino.TreinoResponseDTO;
import com.fittracker.fittrackerpro.entity.Exercicio;
import com.fittracker.fittrackerpro.entity.Treino;
import com.fittracker.fittrackerpro.entity.Usuario;
import com.fittracker.fittrackerpro.mapper.TreinoMapper;
import com.fittracker.fittrackerpro.repository.TreinoRepository;
import com.fittracker.fittrackerpro.repository.UsuarioRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreinoService {

    private final TreinoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final TreinoMapper treinoMapper;

    public TreinoService(TreinoRepository repository, UsuarioRepository usuarioRepository, TreinoMapper treinoMapper) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.treinoMapper = treinoMapper;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public TreinoResponseDTO buscarTreinoPorId(Long id, String emailLogado) {
        var usuario = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Treino treino = repository.findById(id)
                .filter(t -> t.getUsuario().getId().equals(usuario.getId()))
                .orElseThrow(() -> new RuntimeException("Treino não encontrado"));

        return treinoMapper.toResponseDTO(treino);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<TreinoResponseDTO> buscarTreinos(String emailLogado) {
        Usuario usuario = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return repository.findByUsuarioId(usuario.getId())
                .stream()
                .map(treinoMapper::toResponseDTO)
                .toList();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public TreinoResponseDTO criarTreino(TreinoRequestDTO dto, String emailLogado) {
        var usuario = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Treino entity = Treino.builder()
                .usuario(usuario)
                .nomeRotina(dto.nomeRotina())
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
        usuario.registrarTreino();
        usuario.registrarDiaAtivo();

        usuarioRepository.save(usuario);
        repository.save(entity);

        return treinoMapper.toResponseDTO(entity);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public TreinoResponseDTO atualizarTreino(TreinoRequestDTO dto, Long id, String emailLogado) {
        var treino = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Treino não encontrado"));

        if(!treino.getUsuario().getEmail().equals(emailLogado)) {
            throw new RuntimeException("Você não pode alterar treino de outro usuário");
        }

        if(dto.nomeRotina() != null) treino.setNomeRotina(dto.nomeRotina());
        if(dto.intensidadeGeral() != null) treino.setIntensidadeGeral(dto.intensidadeGeral());
        if(dto.duracaoMin() != null) treino.setDuracaoMin(dto.duracaoMin());
        if(dto.observacoes() != null) treino.setObservacoes(dto.observacoes());

        return treinoMapper.toResponseDTO(repository.save(treino));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public void apagarTreino(Long id, String emailLogado) {
        repository.findById(id).orElseThrow(() -> new RuntimeException("Treino não encontrado"));

        var usuario = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new RuntimeException("Você não pode alterar treino de outro usuário"));

        usuario.removerTreino();
        usuario.registrarDiaAtivo();
        usuarioRepository.save(usuario);

        repository.deleteById(id);
    }
}