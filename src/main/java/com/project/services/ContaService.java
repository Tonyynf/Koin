package com.project.services;

import com.project.dto.CategoriaRequestDTO;
import com.project.dto.CategoriaResponseDTO;
import com.project.dto.ContaRequestDTO;
import com.project.dto.ContaResponseDTO;
import com.project.models.Categoria;
import com.project.models.Conta;
import com.project.repositories.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContaService {
    private final ContaRepository contaRepository;

    public ContaResponseDTO createConta(ContaRequestDTO dados) {
        Conta novaConta = new Conta(
                null,
                dados.nome(),
                dados.saldoInicial()
        );

        return converterParaResponseDto(contaRepository.save(novaConta));
    }

    public List<ContaResponseDTO> findAllContas(){
        return contaRepository.findAll()
                .stream()
                .map(this::converterParaResponseDto)
                .toList();
    }

    public ContaResponseDTO findConta(Long id){
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        return converterParaResponseDto(conta);
    }

    public ContaResponseDTO updateConta(Long id, ContaRequestDTO contaRequestDTO){
        Conta contaExistente = contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada!"));

        contaExistente.setNome(contaRequestDTO.nome());
        contaExistente.setSaldoInicial(contaRequestDTO.saldoInicial());

        return converterParaResponseDto(contaRepository.save(contaExistente));
    }

    public void deleteConta(Long id){
        if (!contaRepository.existsById(id)) {
            throw new RuntimeException("Conta não encontrada para exclusão!");
        }

        contaRepository.deleteById(id);
    }

    private ContaResponseDTO converterParaResponseDto(Conta c) {
        return new ContaResponseDTO(
                c.getId(),
                c.getNome(),
                c.getSaldoInicial()
        );
    }
}
