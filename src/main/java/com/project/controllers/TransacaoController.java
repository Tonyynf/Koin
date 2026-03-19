package com.project.controllers;

import com.project.dto.TransacaoDTO;
import com.project.models.Transacao;
import com.project.services.TransacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService){
        this.transacaoService = transacaoService;
    }

    @GetMapping("/saldo/{id}")
    public ResponseEntity<Map<String, Object>> getSaldoReal(@PathVariable Long id) {
        BigDecimal saldo = transacaoService.CalcularValorReal(id);

        Map<String, Object> resposta = Map.of(
                "contaId", id,
                "saldo", saldo,
                "status","sucesso"
        );

        return ResponseEntity.ok(resposta);
    }

    @PostMapping
    public ResponseEntity<Transacao> criarTransacao(@RequestBody TransacaoDTO dados){
        return 0;
    }

    @GetMapping
    public List<Transacao> findAll() {
        return transacaoService.buscarTransacoes();
    }

}
