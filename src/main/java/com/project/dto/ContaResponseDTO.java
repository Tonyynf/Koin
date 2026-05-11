package com.project.dto;

import java.math.BigDecimal;

public record ContaResponseDTO(
        Long id,
        String nome,
        BigDecimal saldoAtual
) {}
