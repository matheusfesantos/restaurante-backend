package com.projeto.projeto_restaurante.models.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AvaliacaoDTO(
   Long produtoId,
   @NotNull(message = "A nota e obrigatoria")
   @Min(value = 0, message = "A nota minima e 0")
   @Max(value = 5, message = "A nota maxima e 5")
   Integer nota

) {
}
