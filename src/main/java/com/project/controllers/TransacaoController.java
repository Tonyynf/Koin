package com.project.controllers;

import com.project.services.TransacaoService;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService){
        this.transacaoService = transacaoService;
    }

    @GetMapping("/saldo/{id}")
    public BigDecimal getSaldoReal(@PathVariable Long id) {
        return transacaoService.CalcularValorReal(id);
    }

}
