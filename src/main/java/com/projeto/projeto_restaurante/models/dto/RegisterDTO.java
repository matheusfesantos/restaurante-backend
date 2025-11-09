package com.projeto.projeto_restaurante.models.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.aspectj.bridge.Message;

public record RegisterDTO (
        @NotBlank(message = "O nome do usuario não pode estar em branco")
        String nome,

        @NotBlank(message = "O email do usuario não pode estar em branco")
        String email,

        @NotNull(message = "A senha do ususario não pode estar em branco")
        String senha)
{ }
