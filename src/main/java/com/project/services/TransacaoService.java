package com.project.services;

import com.project.dto.TransacaoRequestDTO;
import com.project.dto.TransacaoResponseDTO;
import com.project.models.Categoria;
import com.project.models.Conta;
import com.project.models.TipoTransacao;
import com.project.models.Transacao;
import com.project.repositories.CategoriaRepository;
import com.project.repositories.ContaRepository;
import com.project.repositories.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransacaoService {
    private final CategoriaRepository categoriaRepository;
    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;

    public TransacaoResponseDTO createTransacao(TransacaoRequestDTO dados){
        Conta conta = contaRepository.findById(dados.contaId()).orElseThrow(() -> new RuntimeException("Conta não encontrada!"));

        Categoria categoria = categoriaRepository.findById(dados.categoriaId()).orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));

        Transacao novaTransacao = new Transacao(
                null,
                dados.descricao(),
                dados.valor(),
                LocalDateTime.now(),
                dados.tipo(),
                conta,
                categoria
        );

        return converterParaResponseDto(transacaoRepository.save(novaTransacao));
    }

    public List<TransacaoResponseDTO> findAllTransacoes(){
        return transacaoRepository.findAll()
                .stream()
                .map(this::converterParaResponseDto)
                .toList();
    }

    public TransacaoResponseDTO findTransacao(Long id){
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada!"));;

        return converterParaResponseDto(transacao);
    }

    public TransacaoResponseDTO updateTransacao(Long id, TransacaoRequestDTO transacaoRequestDTO){
        Transacao transacaoExistente = transacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada!"));

        Conta conta = contaRepository.findById(transacaoRequestDTO.contaId())
                .orElseThrow(() -> new RuntimeException("Conta não encontrada!"));

        Categoria categoria = categoriaRepository.findById(transacaoRequestDTO.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));

        transacaoExistente.setDescricao(transacaoRequestDTO.descricao());
        transacaoExistente.setValor(transacaoRequestDTO.valor());
        transacaoExistente.setTipo(transacaoRequestDTO.tipo());
        transacaoExistente.setConta(conta);
        transacaoExistente.setCategoria(categoria);

        return converterParaResponseDto(transacaoRepository.save(transacaoExistente));
    }

    public void deleteTransacao(Long id){
        if (!transacaoRepository.existsById(id)) {
            throw new RuntimeException("Transação não encontrada para exclusão!");
        }

        transacaoRepository.deleteById(id);
    }

    public BigDecimal CalcularValorReal(Long contaId){
        Conta conta = contaRepository.findById(contaId).orElseThrow();

        BigDecimal entradas = transacaoRepository.somarPorTipoEConta(TipoTransacao.RECEITA, contaId);

        BigDecimal saidas = transacaoRepository.somarPorTipoEConta(TipoTransacao.DESPESA, contaId);

        BigDecimal totalEntradas = (entradas != null) ? entradas : BigDecimal.ZERO;
        BigDecimal totalSaidas = (saidas != null) ? saidas : BigDecimal.ZERO;

        return conta.getSaldoInicial().add(totalEntradas).subtract(totalSaidas);
    }

    //converter requisição para DTO
    private TransacaoResponseDTO converterParaResponseDto(Transacao t) {
        return new TransacaoResponseDTO(
                t.getId(), t.getDescricao(), t.getValor(),
                t.getTipo(), t.getData(),
                t.getCategoria().getNome(), t.getConta().getNome()
        );
    }
}
