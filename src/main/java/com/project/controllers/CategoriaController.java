package com.project.controllers;

import com.project.dto.CategoriaRequestDTO;
import com.project.dto.CategoriaResponseDTO;
import com.project.services.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {
    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> findAllCategorias() {
        return ResponseEntity.ok(categoriaService.findAllCategorias());
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> findCategoria(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.findCategoria(id));
    }
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> createCategoria(@RequestBody CategoriaRequestDTO categoriaRequestDTO){
        return ResponseEntity.ok(categoriaService.createCategoria(categoriaRequestDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> updateCategoria(@PathVariable Long id, @RequestBody CategoriaRequestDTO categoriaRequestDTO) {
        return ResponseEntity.ok(categoriaService.updateCategoria(id, categoriaRequestDTO));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id){
        categoriaService.deleteCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
