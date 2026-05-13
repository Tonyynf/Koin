package com.project.controllers;

import com.project.dto.ContaRequestDTO;
import com.project.dto.ContaResponseDTO;
import com.project.services.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contas")
@RequiredArgsConstructor
public class ContaController {
    private final ContaService contaService;

    @PostMapping
    public ResponseEntity<ContaResponseDTO> createConta(@RequestBody ContaRequestDTO dados) {
        ContaResponseDTO response = contaService.createConta(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    public ResponseEntity<List<ContaResponseDTO>> findAllContas() {
        return ResponseEntity.ok(contaService.findAllContas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaResponseDTO> findConta(@PathVariable Long id) {
        return ResponseEntity.ok(contaService.findConta(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ContaResponseDTO> updateConta(@PathVariable Long id, @RequestBody ContaRequestDTO dados) {
        return ResponseEntity.ok(contaService.updateConta(id, dados));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConta(@PathVariable Long id) {
        contaService.deleteConta(id);
        return ResponseEntity.noContent().build();
    }
}