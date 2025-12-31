package com.fittracker.fittrackerpro.entity;

import com.fittracker.fittrackerpro.entity.enums.ObjetivoUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_rotina")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rotina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rotina")
    private Long id;

    private String nome;
    private String descricao;

    @PrePersist
    protected void onCreate() {
        this.descricao = "Sem Detalhes";
    }

    public void adicionarDia(DiaRotina dia) {
        dias.add(dia);
        dia.setRotina(this);
    }

    public void removerDia(DiaRotina dia) {
        dias.remove(dia);
        dia.setRotina(null);
    }

    @OneToMany(
            mappedBy = "rotina",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<DiaRotina> dias = new ArrayList<>();

    // se for null → rotina pública (explorar)
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
