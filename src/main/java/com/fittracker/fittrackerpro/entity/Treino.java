package com.fittracker.fittrackerpro.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_treinos")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Treino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_treino")
    private Long id;

    @Column(nullable = false)
    private String nomeTreino;

    @Column(nullable = false)
    private Integer duracaoMin;

    @Column(nullable = false)
    private Integer intensidadeGeral;

    // Opcional
    private String observacoes;

    @Column(nullable = false, updatable = false)
    private LocalDate criado_em;

    @PrePersist
    protected void onCreate() {
        this.criado_em = LocalDate.now();
    }

    @OneToOne(mappedBy = "treino")
    private DiaRotina diaRotina;

    @OneToMany(mappedBy = "treino", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    @ToString.Exclude
    private List<Exercicio> exercicios = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    @ToString.Exclude // 3. Evita loop infinito
    private Usuario usuario;
}