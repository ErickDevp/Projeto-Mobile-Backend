package com.fittracker.fittrackerpro.model;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Treino")
public class Treino {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Usuario", nullable = false)
	private Usuario usuario;
	
	private String nomeRotina;
	private LocalDateTime dataTreino = LocalDateTime.now();
	private Integer duracaoMin;
	private Integer intensidadeGeral;
	private String observacoes;
	
	@OneToMany(mappedBy = "treino", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exercicio> exercicios = new ArrayList<>();
	
	
	public Treino() {}
    

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

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
    
    public List<Exercicio> getExercicios() { return exercicios; }
    public void setExercicios(List<Exercicio> exercicios) { this.exercicios = exercicios; }

}
