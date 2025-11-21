package com.projeto.projeto_restaurante.repositories;

import com.projeto.projeto_restaurante.models.entity.Avaliacao;
import com.projeto.projeto_restaurante.models.entity.Produtos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Parameter;
import java.util.UUID;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    boolean existsByUsuario_Id(UUID usuarioId);

    boolean existsByUsuario_IdAndProduto(UUID usuarioId, Produtos produto);

    boolean existsByUsuario_IdAndProduto_Id(UUID usuarioId, Long produtoId);

    @Query("SELECT COALESCE(AVG(a.nota), 0.0) FROM Avaliacao a WHERE a.produto.id = :produtoId")
    Double calcularMediaPorProduto (@Param("produtoId") Long produtoId);
}
