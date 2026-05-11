package com.project.controllers;

import com.project.dto.TransacaoRequestDTO;
import com.project.dto.TransacaoResponseDTO;
import com.project.services.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
@RequiredArgsConstructor
public class TransacaoController {
    private final TransacaoService transacaoService;

    @GetMapping
    public ResponseEntity<List<TransacaoResponseDTO>> findAllTransacoes() {
        return ResponseEntity.ok(transacaoService.findAllTransacoes());
    }
    @GetMapping("/{id}")
    public ResponseEntity<TransacaoResponseDTO> findTransacao(@PathVariable Long id) {
        return ResponseEntity.ok(transacaoService.findTransacao(id));
    }
    @PostMapping
    public ResponseEntity<TransacaoResponseDTO> createTransacao(@RequestBody TransacaoRequestDTO transacaoRequestDTO){
        return ResponseEntity.ok(transacaoService.createTransacao(transacaoRequestDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<TransacaoResponseDTO> updateTransacao(@PathVariable Long id, @RequestBody TransacaoRequestDTO transacaoRequestDTO) {
        return ResponseEntity.ok(transacaoService.updateTransacao(id, transacaoRequestDTO));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransacao(@PathVariable Long id){
        transacaoService.deleteTransacao(id);
        return ResponseEntity.noContent().build();
    }
}
