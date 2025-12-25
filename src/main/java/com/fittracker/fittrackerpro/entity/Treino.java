package com.fittracker.fittrackerpro.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_treinos")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Treino {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_treino")
    private Long id;

    @Column(nullable = false)
    private String nomeRotina;

    @Column(nullable = false)
    private Integer duracaoMin;

    @Column(nullable = false)
    private Integer intensidadeGeral;

    // Opcional
	private String observacoes;

    @Column(nullable = false, updatable = false)
    private LocalDateTime criado_em;

    @PrePersist
    protected void onCreate() {
        this.criado_em = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "treino", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exercicio> exercicios = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}
