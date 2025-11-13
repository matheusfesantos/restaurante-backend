package com.projeto.projeto_restaurante.repositories;

import com.projeto.projeto_restaurante.models.entity.Comentarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentariosRepository extends JpaRepository<Comentarios, Long> {
    List<Comentarios> findByProdutoId(Long produtoId);
}
