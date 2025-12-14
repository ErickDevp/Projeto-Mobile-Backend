package com.fittracker.fittrackerpro.dto;

// DTO simples para enviar dados de exercício de volta
public class ExercicioResponseDTO {

    private Long id;
    private String nomeExercicio;
    private Integer series;
    private String repeticoes;
    private Double cargaTotalKg;
    private String observacoesEx;

    // Construtor Vazio
    public ExercicioResponseDTO() {}

    // Getters e Setters (Manuais ou Lombok)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNomeExercicio() { return nomeExercicio; }
    public void setNomeExercicio(String nomeExercicio) { this.nomeExercicio = nomeExercicio; }
    public Integer getSeries() { return series; }
    public void setSeries(Integer series) { this.series = series; }
    public String getRepeticoes() { return repeticoes; }
    public void setRepeticoes(String repeticoes) { this.repeticoes = repeticoes; }
    public Double getCargaTotalKg() { return cargaTotalKg; }
    public void setCargaTotalKg(Double cargaTotalKg) { this.cargaTotalKg = cargaTotalKg; }
    public String getObservacoesEx() { return observacoesEx; }
    public void setObservacoesEx(String observacoesEx) { this.observacoesEx = observacoesEx; }
}