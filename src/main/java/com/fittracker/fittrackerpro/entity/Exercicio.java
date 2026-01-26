package com.fittracker.fittrackerpro.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_exercicios")
@Builder
@Getter
@Setter
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
    private String repeticoes;

    @Column(nullable = false)
    private Double cargaTotalKg;

    // Opcional
	private String observacoesEx;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_treino", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private Treino treino;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercicio exercicio = (Exercicio) o;
        return id != null && id.equals(exercicio.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
