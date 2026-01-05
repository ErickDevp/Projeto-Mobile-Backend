package com.fittracker.fittrackerpro.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_rotina")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rotina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rotina")
    private Long id;

    private String nome;
    private LocalDate dataInicio;

    public void adicionarDia(DiaRotina dia) {
        if (this.dias == null) {
            this.dias = new ArrayList<>();
        }
        this.dias.add(dia);
        dia.setRotina(this);
    }

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "rotina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiaRotina> dias = new ArrayList<>();
}
