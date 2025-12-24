package com.fittracker.fittrackerpro.dto.treino;

import com.fittracker.fittrackerpro.dto.exercicio.ExercicioRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.ArrayList; 

public class TreinoRequestDTO {

    private String nomeRotina;
    private Integer duracaoMin;
    private Integer intensidadeGeral;
    private String observacoes;

    @NotEmpty(message = "A lista de exercícios não pode ser vazia")
    @Valid
    private List<ExercicioRequestDTO> exercicios = new ArrayList<>();

 
    public TreinoRequestDTO() {}
    

    public String getNomeRotina() {
        return nomeRotina;
    }

    public void setNomeRotina(String nomeRotina) {
        this.nomeRotina = nomeRotina;
    }

    public Integer getDuracaoMin() {
        return duracaoMin;
    }

    public void setDuracaoMin(Integer duracaoMin) {
        this.duracaoMin = duracaoMin;
    }

    public Integer getIntensidadeGeral() {
        return intensidadeGeral;
    }

    public void setIntensidadeGeral(Integer intensidadeGeral) {
        this.intensidadeGeral = intensidadeGeral;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public List<ExercicioRequestDTO> getExercicios() {
        return exercicios;
    }

    public void setExercicios(List<ExercicioRequestDTO> exercicios) {
        this.exercicios = exercicios;
    }
}