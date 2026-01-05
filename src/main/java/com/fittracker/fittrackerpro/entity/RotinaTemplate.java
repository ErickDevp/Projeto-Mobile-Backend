package com.fittracker.fittrackerpro.entity;


import com.fittracker.fittrackerpro.entity.enums.NivelTreino;
import com.fittracker.fittrackerpro.entity.enums.ObjetivoUsuario;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_rotina_template")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RotinaTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rotina_template")
    private Long id;

    private String nome;
    private String descricao;

    @Enumerated(EnumType.STRING)
    private ObjetivoUsuario objetivo;

    @Enumerated(EnumType.STRING)
    private NivelTreino nivel;

    public void adicionarDia(DiaRotina dia) {
        if (this.dias == null) {
            this.dias = new ArrayList<>();
        }
        this.dias.add(dia);
        dia.setRotinaTemplate(this);
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiaRotina> dias;
}
