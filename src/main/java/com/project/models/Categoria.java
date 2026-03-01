package com.project.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_categoria")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String conta;

    @Column(nullable = false)
    private BigDecimal limiteMensal; // Sabendo se ultrapassou ou não o limite mensal...
    @Column(nullable = false)
    private String corHex;           // será determinada uma cor( menos de 50%(verde), entre 50% e 80%(amarelo), 100% ou mais(vermelho))
}
