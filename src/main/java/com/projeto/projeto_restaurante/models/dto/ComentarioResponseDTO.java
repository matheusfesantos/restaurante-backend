package com.projeto.projeto_restaurante.models.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ComentarioResponseDTO(
        Long comentario_id,
        String produto,
        Long produto_id,
        String usuario,
        UUID usuario_id,
        String comentario,
        LocalDate dataComentario
) {
}
