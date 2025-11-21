package com.projeto.projeto_restaurante.models.dto;

import java.time.LocalDate;
import java.util.UUID;

public record AvaliacaoResponseDTO(
    Long id,
    String produtoNome,
    Long produtoId,
    String usuarioNome,
    UUID usuarioId,
    Integer nota,
    LocalDate data
) {
}