package com.fittracker.fittrackerpro.entity;

import com.fittracker.fittrackerpro.entity.enums.DiaSemana;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_dia")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiaRotina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DiaSemana diaSemana;

    @ManyToOne
    @JoinColumn(name = "id_rotina", nullable = false)
    private Rotina rotina;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_treino", nullable = false)
    private Treino treino;

    @ManyToOne
    @JoinColumn(name = "id_rotina_template")
    private RotinaTemplate rotinaTemplate;
}
