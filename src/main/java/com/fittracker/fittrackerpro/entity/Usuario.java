package com.fittracker.fittrackerpro.entity;

import com.fittracker.fittrackerpro.entity.enums.ObjetivoUsuario;
import com.fittracker.fittrackerpro.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@Table(name = "tb_usuarios")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String fotoPerfil;

    @Enumerated(EnumType.STRING)
    private ObjetivoUsuario objetivo;

    @Column(nullable = false)
    private Integer totalTreinos;

    @Column(nullable = false)
    private Integer diasAtivo;

    private LocalDate ultimoDiaAtivo;

    @Column(nullable = false, updatable = false)
    private LocalDateTime criado_em;

    @PrePersist
    protected void onCreate() {
        this.criado_em = LocalDateTime.now();
        this.objetivo = ObjetivoUsuario.HIPERTROFIA;
        this.diasAtivo = 0;
        this.totalTreinos = 0;
    }

    public void registrarTreino() {
        this.totalTreinos++;
    }

    public void removerTreino() {
        if (this.totalTreinos > 0) {
            this.totalTreinos--;
        }
    }

    public void registrarDiaAtivo() {
        LocalDate hoje = LocalDate.now();

        // Primeiro treino/post
        if (ultimoDiaAtivo == null) {
            diasAtivo = 1;
            ultimoDiaAtivo = hoje;
            return;
        }

        long diasDiferenca = ChronoUnit.DAYS.between(ultimoDiaAtivo, hoje);

        if (diasDiferenca == 0) {
            return;
        }

        if (diasDiferenca == 1) {
            // Dia consecutivo
            diasAtivo++;
        } else {
            // Quebrou o streak
            diasAtivo = 1;
        }

        ultimoDiaAtivo = hoje;
    }


    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Treino> treino;
}