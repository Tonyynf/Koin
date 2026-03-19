package com.project.dto;

import com.project.models.TipoTransacao;

import java.math.BigDecimal;

public record TransacaoDTO(
        String descricao,
        BigDecimal valor,
        TipoTransacao tipo,
        Long contaId,
        Long categoriaId
) {}
