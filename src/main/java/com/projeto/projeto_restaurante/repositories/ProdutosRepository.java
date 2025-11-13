package com.projeto.projeto_restaurante.repositories;

import com.projeto.projeto_restaurante.models.entity.Produtos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutosRepository extends JpaRepository<Produtos, Long> {
}
