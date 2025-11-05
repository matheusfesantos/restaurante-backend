package com.projeto.projeto_restaurante.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;

public record RegisterDTO (

        @Max(value = 100)
        String nome,

        @Email
        String email,

        @Max(value = 100)
        String senha)
{ }
