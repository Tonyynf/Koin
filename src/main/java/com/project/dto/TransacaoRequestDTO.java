package com.project.dto;

import com.project.models.TipoTransacao;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record TransacaoRequestDTO(
        @NotBlank(message = "A transação precisa de uma descrição")
        String descricao,

        @NotBlank(message = "O valor não pode ser nulo")
        @Positive(message = "O valor deve ser acima de 0")
        BigDecimal valor,

        @NotBlank(message = "A transação precisa de um tipo")
        TipoTransacao tipo,

        @NotBlank(message = "O ID não pode ser nulo")
        @Positive(message = "O ID deve ser acima de 0")
        Long contaId,

        @NotBlank(message = "O ID não pode ser nulo")
        @Positive(message = "O ID deve ser acima de 0")
        Long categoriaId
) {}
