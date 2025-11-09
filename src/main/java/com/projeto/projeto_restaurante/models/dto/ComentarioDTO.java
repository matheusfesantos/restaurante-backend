package com.projeto.projeto_restaurante.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ComentarioDTO (
        @NotNull (message = "O Id do produto não pode ser nulo")
        Long produto_id,
        @NotBlank (message = "O comentario não pode estar em branco")
        String comentario
)
{}
