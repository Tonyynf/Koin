package com.project.dto;

import com.project.models.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoResponseDTO(
        Long id,
        String descricao,
        BigDecimal valor,
        TipoTransacao tipo,
        LocalDateTime data,
        String nomeCategoria,
        String nomeConta
) {}
