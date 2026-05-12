package com.project.services;

import com.project.dto.CategoriaRequestDTO;
import com.project.dto.CategoriaResponseDTO;
import com.project.models.Categoria;
import com.project.repositories.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public CategoriaResponseDTO createCategoria(CategoriaRequestDTO dados){
        Categoria novaCategoria = new Categoria(
                null,
                dados.nome(),
                dados.limiteMensal(),
                dados.corHex()
        );

        return converterParaResponseDto(categoriaRepository.save(novaCategoria));
    }

    public List<CategoriaResponseDTO> findAllCategorias(){
        return categoriaRepository.findAll()
                .stream()
                .map(this::converterParaResponseDto)
                .toList();
    }

    public CategoriaResponseDTO findCategoria(Long id){
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        return converterParaResponseDto(categoria);
    }

    public CategoriaResponseDTO updateCategoria(Long id, CategoriaRequestDTO categoriaRequestDTO){
        Categoria categoriaExistente = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));

        categoriaExistente.setNome(categoriaRequestDTO.nome());
        categoriaExistente.setLimiteMensal(categoriaRequestDTO.limiteMensal());
        categoriaExistente.setCorHex(categoriaRequestDTO.corHex());


        return converterParaResponseDto(categoriaRepository.save(categoriaExistente));
    }

    public void deleteCategoria(Long id){
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoria não encontrada para exclusão!");
        }

        categoriaRepository.deleteById(id);
    }

    //converter requisição para DTO
    private CategoriaResponseDTO converterParaResponseDto(Categoria c) {
        return new CategoriaResponseDTO(
                c.getId(),
                c.getNome(),
                c.getLimiteMensal(),
                c.getCorHex()
        );
    }

}

