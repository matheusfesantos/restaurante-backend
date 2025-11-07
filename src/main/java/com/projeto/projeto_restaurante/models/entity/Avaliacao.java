package com.projeto.projeto_restaurante.models.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "avaliacao")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "produto_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Produtos produto;

    @JoinColumn(name = "usuario_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuarios usuario;

    private Integer nota;

    private LocalDate data;

    @PrePersist
    public void prePersist(){
        this.data = LocalDate.now();
    }
}