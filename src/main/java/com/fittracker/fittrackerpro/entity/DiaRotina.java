package com.fittracker.fittrackerpro.entity;

import com.fittracker.fittrackerpro.entity.enums.DiaSemana;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_dia")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaRotina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DiaSemana diaSemana;

    @ManyToOne
    @JoinColumn(name = "id_rotina")
    private Rotina rotina;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_treino")
    private Treino treino;
}
