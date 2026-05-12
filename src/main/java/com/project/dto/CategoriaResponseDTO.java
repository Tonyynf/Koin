package com.project.dto;

import java.math.BigDecimal;

public record CategoriaResponseDTO(
        Long id,
        String nome,
        BigDecimal limiteMensal,
        String corHex
) {}
