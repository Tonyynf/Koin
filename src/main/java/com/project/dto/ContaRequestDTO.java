package com.project.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ContaRequestDTO(
        @NotBlank(message = "O nome não pode ser nulo")
        String nome,

        @NotNull(message = "O valor não pode ser nulo")
        BigDecimal saldoInicial
) {}
