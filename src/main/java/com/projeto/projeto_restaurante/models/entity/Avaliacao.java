package com.projeto.projeto_restaurante.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "avaliacoes")
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

    @Column(name = "data_avaliacao")
    private LocalDate data;

    @PrePersist
    public void prePersist(){
        this.data = LocalDate.now();
    }
}