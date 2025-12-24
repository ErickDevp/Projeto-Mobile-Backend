package com.fittracker.fittrackerpro.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_exercicios")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exercicio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_exercicio")
    private Long id;

    @Column(nullable = false)
    private String nomeExercicio;

    @Column(nullable = false)
    private Integer series;

    @Column(nullable = false)
    private Integer repeticoes;

    @Column(nullable = false)
    private Double cargaTotalKg;

    // Opcional
	private String observacoesEx;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_treino", nullable = false)
    private Treino treino;

}
