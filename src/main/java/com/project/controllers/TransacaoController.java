package com.project.controllers;

import com.project.dto.TransacaoDTO;
import com.project.models.Categoria;
import com.project.models.Conta;
import com.project.models.Transacao;
import com.project.repositories.CategoriaRepository;
import com.project.repositories.ContaRepository;
import com.project.repositories.TransacaoRepository;
import com.project.services.TransacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;
    private final ContaRepository contaRepo;
    private final CategoriaRepository catRepo;
    private final TransacaoRepository transacaoRepo;

    public TransacaoController(TransacaoService transacaoService,
                               ContaRepository contaRepo,
                               CategoriaRepository catRepo,
                               TransacaoRepository transacaoRepo) {
        this.transacaoService = transacaoService;
        this.contaRepo = contaRepo;
        this.catRepo = catRepo;
        this.transacaoRepo = transacaoRepo;
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
        Conta conta = contaRepo.findById(dados.contaId()).orElseThrow(() -> new RuntimeException("Conta não encontrada!"));

        Categoria categoria = catRepo.findById(dados.categoriaId()).orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));

        Transacao novaTransacao = new Transacao(
          null,
          dados.descricao(),
          dados.valor(),
          LocalDateTime.now(),
          dados.tipo(),
          conta,
          categoria
        );

        return ResponseEntity.ok(transacaoRepo.save(novaTransacao));
    }

    @GetMapping
    public List<Transacao> findAll() {
        return transacaoService.buscarTransacoes();
    }

}
