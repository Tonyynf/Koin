package com.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CategoriaRequestDTO(
        @NotBlank(message = "O nome não pode ser nulo")
        String nome,

        @NotNull(message = "É preciso informar um limite mensal")
        @Positive(message = "O valor precisa ser positivo")
        BigDecimal limiteMensal,

        @NotBlank(message = "A categoria precisa de uma cor")
        String corHex
) {}
