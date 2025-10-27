package com.fittracker.fittrackerpro.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

// DTO simples para enviar dados de treino de volta
public class TreinoResponseDTO {

    private Long id;
    private String nomeRotina;
    private LocalDateTime dataTreino;
    private Integer duracaoMin;
    private Integer intensidadeGeral;
    private String observacoes;
    private List<ExercicioResponseDTO> exercicios = new ArrayList<>(); // Lista com DTOs de exercício

    // Construtor Vazio
    public TreinoResponseDTO() {}

    // Getters e Setters (Manuais ou Lombok)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNomeRotina() { return nomeRotina; }
    public void setNomeRotina(String nomeRotina) { this.nomeRotina = nomeRotina; }
    public LocalDateTime getDataTreino() { return dataTreino; }
    public void setDataTreino(LocalDateTime dataTreino) { this.dataTreino = dataTreino; }
    public Integer getDuracaoMin() { return duracaoMin; }
    public void setDuracaoMin(Integer duracaoMin) { this.duracaoMin = duracaoMin; }
    public Integer getIntensidadeGeral() { return intensidadeGeral; }
    public void setIntensidadeGeral(Integer intensidadeGeral) { this.intensidadeGeral = intensidadeGeral; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    public List<ExercicioResponseDTO> getExercicios() { return exercicios; }
    public void setExercicios(List<ExercicioResponseDTO> exercicios) { this.exercicios = exercicios; }
}