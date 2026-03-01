package com.project.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import lombok.*;

@Entity
@Table(name = "tb_transacoes")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDateTime data;

    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;
}
