package com.projeto.projeto_restaurante.models.dto;

import jakarta.validation.constraints.NotBlank;

public record ComentarioAtualizarDTO(
        @NotBlank(message = "O comentário não pode estar vazio")
        String comentario
) {
}
