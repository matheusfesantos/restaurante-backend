package com.projeto.projeto_restaurante.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoDTO(
        String foto,
        @NotBlank(message = "O nome do produto não pode estar em branco")
        String nome,
        @NotNull(message = "O preco do produto não pode ser nulo")
        Double preco
){
}
