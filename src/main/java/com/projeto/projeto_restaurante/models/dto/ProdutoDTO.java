package com.projeto.projeto_restaurante.models.dto;

/**
 * Data Transfer Object (DTO) representing product information.
 * This record is used to encapsulate details about a product such as its photo, name, and price.
 * It is primarily used in service-layer operations where transferring lightweight and essential product
 * data is required, instead of dealing directly with the product entity.
 */
public record ProdutoDTO(
        String foto,
        String nome,
        Double preco
){
}
