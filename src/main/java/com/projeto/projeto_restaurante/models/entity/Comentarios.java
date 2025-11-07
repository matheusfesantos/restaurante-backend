package com.projeto.projeto_restaurante.models.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "comentarios")
public class Comentarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "produto_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Produtos produto;

    @JoinColumn(name = "usuario_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuarios usuario;

    private String comentario;

    @Column(name = "data_comentario")
    private LocalDate dataComentario;

    @PrePersist
    public void prePersist(){
        this.dataComentario =  LocalDate.now();
    }
}
