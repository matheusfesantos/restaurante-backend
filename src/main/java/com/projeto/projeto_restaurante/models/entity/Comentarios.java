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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produtos produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuarios usuario;

    private String comentario;

    @Column(name = "data_comentario")
    private LocalDate dataComentario;

    @PrePersist
    public void prePersist(){
        this.dataComentario =  LocalDate.now();
    }
}
