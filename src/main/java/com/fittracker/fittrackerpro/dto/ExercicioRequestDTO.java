package com.fittracker.fittrackerpro.dto; 

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ExercicioRequestDTO {

    @NotBlank(message = "Nome do exercício não pode ser vazio")
    private String nomeExercicio;

    @NotNull(message = "Número de séries é obrigatório")
    @Positive(message = "Número de séries deve ser positivo")
    private Integer series;

    private String repeticoes; 

    @NotNull(message = "Carga é obrigatória (pode ser 0.0)")
    private Double cargaTotalKg;

    private String observacoesEx;

    public ExercicioRequestDTO() {}


    public String getNomeExercicio() {
        return nomeExercicio;
    }

    public void setNomeExercicio(String nomeExercicio) {
        this.nomeExercicio = nomeExercicio;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public String getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(String repeticoes) {
        this.repeticoes = repeticoes;
    }

    public Double getCargaTotalKg() {
        return cargaTotalKg;
    }

    public void setCargaTotalKg(Double cargaTotalKg) {
        this.cargaTotalKg = cargaTotalKg;
    }

    public String getObservacoesEx() {
        return observacoesEx;
    }

    public void setObservacoesEx(String observacoesEx) {
        this.observacoesEx = observacoesEx;
    }
}