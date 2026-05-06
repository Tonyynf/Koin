package com.project.controllers;

import com.project.dto.TransacaoRequestDTO;
import com.project.dto.TransacaoResponseDTO;
import com.project.models.Categoria;
import com.project.models.Conta;
import com.project.models.Transacao;
import com.project.repositories.CategoriaRepository;
import com.project.repositories.ContaRepository;
import com.project.repositories.TransacaoRepository;
import com.project.services.TransacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transacoes")
@RequiredArgsConstructor
public class TransacaoController {
    private final TransacaoService transacaoService;
    private final ContaRepository contaRepo;
    private final CategoriaRepository catRepo;
    private final TransacaoRepository transacaoRepo;

    @GetMapping
    public List<Transacao> findAll() {
        return transacaoService.findAllTransacoes();
    }
    @PostMapping
    public ResponseEntity<Transacao> createTransacao(@RequestBody TransacaoRequestDTO transacaoRequestDTO){
        return transacaoService.createTransacao(transacaoRequestDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Transacao> updateTransacao(@PathVariable Long id, @RequestBody TransacaoRequestDTO transacaoRequestDTO) {
        return transacaoService.updateTransacao(id, transacaoRequestDTO);
    }
    @DeleteMapping("/{id}")
    public void deleteTransacao(@PathVariable Long id){
        transacaoService.deleteTransacao(id);
    }


//    @GetMapping("/saldo/{id}")
//    public ResponseEntity<Map<String, Object>> ConsultarSaldoReal(@PathVariable Long id) {
//        BigDecimal saldo = transacaoService.CalcularValorReal(id);
//
//        Map<String, Object> resposta = Map.of(
//                "contaId", id,
//                "saldo", saldo,
//                "status","sucesso"
//        );
//
//        return ResponseEntity.ok(resposta);
//    }
//    @GetMapping("/categoria/{categoriaId}")
//    public ResponseEntity<List<Transacao>> buscarPorCategoria(@PathVariable Long categoriaId){
//        List<Transacao> transacoes = transacaoRepo.findByContaId(categoriaId);
//        return ResponseEntity.ok(transacoes);
//    }
//    @GetMapping("/conta/{contaId}")
//    public ResponseEntity<List<Transacao>> buscarPorConta(@PathVariable Long contaId){
//        List<Transacao> transacoes = transacaoRepo.findByContaId(contaId);
//        return ResponseEntity.ok(transacoes);
//    }
//    @GetMapping("/conta/{contaId}")
//    public ResponseEntity<List<Transacao>> listarTransacoesPorData(@PathVariable Long contaId){
//        List<Transacao> transacoes = transacaoRepo.findByContaIdOrderByDataDesc(contaId);
//        return ResponseEntity.ok(transacoes);
//    }
//    @GetMapping("/conta/{contaId}")
//    public ResponseEntity<List<Transacao>> listarTransacoesPorCategoriaEValor(@PathVariable Long contaId, Long categoriaId){
//        List<Transacao> transacoes = transacaoRepo.findByContaIdAndCategoriaIdOrderByValorDesc(contaId, categoriaId);
//        return ResponseEntity.ok(transacoes);
//    }
//    @GetMapping("/conta/{contaId}")
//    public ResponseEntity<List<Transacao>> listarTransacoesValorEspecifico(@PathVariable Long contaId, BigDecimal valor){
//        List<Transacao> transacoes = transacaoRepo.findByContaIdAndValorGreaterThanEqual(contaId, valor);
//        return ResponseEntity.ok(transacoes);
//    }
}
