package com.fittracker.fittrackerpro.service; // Use seu pacote completo

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fittracker.fittrackerpro.dto.ExercicioRequestDTO;
import com.fittracker.fittrackerpro.dto.TreinoRequestDTO;
import com.fittracker.fittrackerpro.dto.ExercicioResponseDTO; // NOVO: DTO de Resposta
import com.fittracker.fittrackerpro.dto.TreinoResponseDTO;   // NOVO: DTO de Resposta
import com.fittracker.fittrackerpro.model.Exercicio;
import com.fittracker.fittrackerpro.model.Treino;
import com.fittracker.fittrackerpro.model.Usuario;
import com.fittracker.fittrackerpro.repository.TreinoRepository;
import com.fittracker.fittrackerpro.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors; // Necessário para o mapeamento

@Service
public class TreinoService {

    private final TreinoRepository treinoRepository;
    private final UsuarioRepository usuarioRepository;

    public TreinoService(TreinoRepository treinoRepository,
                         UsuarioRepository usuarioRepository) {
        this.treinoRepository = treinoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Registra uma nova sessão de treino completa.
     */
    @Transactional
    public Treino registrarNovoTreino(TreinoRequestDTO dto, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + usuarioId));

        Treino novoTreino = new Treino();
        novoTreino.setUsuario(usuario);
        novoTreino.setNomeRotina(dto.getNomeRotina());
        novoTreino.setDataTreino(LocalDateTime.now());
        novoTreino.setDuracaoMin(dto.getDuracaoMin());
        novoTreino.setIntensidadeGeral(dto.getIntensidadeGeral());
        novoTreino.setObservacoes(dto.getObservacoes());
        novoTreino.setExercicios(new ArrayList<>());

        if (dto.getExercicios() != null) {
            for (ExercicioRequestDTO exDto : dto.getExercicios()) {
                Exercicio novoExercicio = new Exercicio();
                novoExercicio.setNomeExercicio(exDto.getNomeExercicio());
                novoExercicio.setSeries(exDto.getSeries());
                novoExercicio.setRepeticoes(exDto.getRepeticoes());
                novoExercicio.setCargaTotalKg(exDto.getCargaTotalKg());
                novoExercicio.setObservacoesEx(exDto.getObservacoesEx());
                novoExercicio.setTreino(novoTreino);
                novoTreino.getExercicios().add(novoExercicio);
            }
        }
        return treinoRepository.save(novoTreino);
    }

    /**
     * Busca o histórico de treinos de um usuário e mapeia para DTOs de resposta.
     */
    @Transactional(readOnly = true)
    public List<TreinoResponseDTO> buscarHistoricoPorUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
             throw new RuntimeException("Usuário não encontrado com ID: " + usuarioId);
        }

        // 1. Busca as entidades Treino do banco
        List<Treino> treinos = treinoRepository.findByUsuarioIdOrderByDataTreinoDesc(usuarioId);

        // 2. Mapeia a lista de Entidades para a lista de DTOs de Resposta
        return treinos.stream()
                .map(this::mapTreinoToResponseDTO) // Mapeia cada treino
                .collect(Collectors.toList()); // Coleta em uma lista
    }

    // --- MÉTODOS PRIVADOS DE MAPEAMENTO ---

    // Mapeia Entidade Treino -> TreinoResponseDTO
    private TreinoResponseDTO mapTreinoToResponseDTO(Treino treino) {
        TreinoResponseDTO dto = new TreinoResponseDTO();
        dto.setId(treino.getId());
        dto.setNomeRotina(treino.getNomeRotina());
        dto.setDataTreino(treino.getDataTreino());
        dto.setDuracaoMin(treino.getDuracaoMin());
        dto.setIntensidadeGeral(treino.getIntensidadeGeral());
        dto.setObservacoes(treino.getObservacoes());

        // Mapeia a lista de Entidades Exercicio para a lista de ExercicioResponseDTO
        if (treino.getExercicios() != null) {
            List<ExercicioResponseDTO> exDtos = treino.getExercicios().stream()
                    .map(this::mapExercicioToResponseDTO)
                    .collect(Collectors.toList());
            dto.setExercicios(exDtos);
        }

        return dto;
    }

    // Mapeia Entidade Exercicio -> ExercicioResponseDTO
    private ExercicioResponseDTO mapExercicioToResponseDTO(Exercicio exercicio) {
        ExercicioResponseDTO dto = new ExercicioResponseDTO();
        dto.setId(exercicio.getId());
        dto.setNomeExercicio(exercicio.getNomeExercicio());
        dto.setSeries(exercicio.getSeries());
        dto.setRepeticoes(exercicio.getRepeticoes());
        dto.setCargaTotalKg(exercicio.getCargaTotalKg());
        dto.setObservacoesEx(exercicio.getObservacoesEx());
        return dto;
    }
}