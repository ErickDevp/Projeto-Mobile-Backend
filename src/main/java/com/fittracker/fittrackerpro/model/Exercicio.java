package com.fittracker.fittrackerpro.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "EXERCICIO_REGISTRADO")
public class Exercicio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_treino", nullable = false)
	private Treino treino;
	
	private String nomeExercicio;
	private Integer series;
	private String repeticoes;
	private Double cargaTotalKg;
	private String observacoesEx;
	
	public Exercicio() {}

   
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Treino getTreino() { return treino; }
    public void setTreino(Treino treino) { this.treino = treino; }

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
