package com.projeto.projeto_restaurante.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "avaliacao")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "produto_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Produtos produto;

    @Column(name = "usuario_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuarios usuario;

    private Integer nota;

    private LocalDate data;

    @PrePersist
    public void prePersist(){
        this.data = LocalDate.now();
    }
}