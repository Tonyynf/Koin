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

    public ResponseEntity<Transacao> createTransacao(@RequestBody TransacaoRequestDTO dados){
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

        return ResponseEntity.ok(transacaoRepository.save(novaTransacao));
    }

    public List<Transacao> findAllTransacoes(){
        return transacaoRepository.findAll();
    }

    public ResponseEntity<Transacao> updateTransacao(Long id, TransacaoRequestDTO transacaoRequestDTO){
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

        return ResponseEntity.ok(transacaoRepository.save(transacaoExistente));
    }

    public void deleteTransacao(Long id){
        if (!transacaoRepository.existsById(id)) {
            throw new RuntimeException("Transação não encontrada para exclusão!");
        }

        transacaoRepository.deleteById(id);
    }


//    public List<TransacaoResponseDTO> buscarExtrato(Long contaId, BigDecimal min) {
//        List<Transacao> listaDoBanco;
//
//        if (min == null) {
//            // Busca simples por conta
//            listaDoBanco = transacaoRepository.findByContaIdOrderByDataDesc(contaId);
//        } else {
//            // Busca com filtro de valor mínimo
//            listaDoBanco = transacaoRepository.findByContaIdAndValorGreaterThanEqual(contaId, min);
//        }
//
//        // A mágica do Stream: transforma cada Transacao em TransacaoDTO
//        return listaDoBanco.stream()
//                .map(this::converterParaResponseDto)
//                .toList();
//    }
//
    public BigDecimal CalcularValorReal(Long contaId){
        Conta conta = contaRepository.findById(contaId).orElseThrow();

        BigDecimal entradas = transacaoRepository.somarPorTipoEConta(TipoTransacao.RECEITA, contaId);

        BigDecimal saidas = transacaoRepository.somarPorTipoEConta(TipoTransacao.DESPESA, contaId);

        BigDecimal totalEntradas = (entradas != null) ? entradas : BigDecimal.ZERO;
        BigDecimal totalSaidas = (saidas != null) ? saidas : BigDecimal.ZERO;

        return conta.getSaldoInicial().add(totalEntradas).subtract(totalSaidas);
    }
//
//    public List<Transacao> buscaPorConta(Long contaId) {
//        return transacaoRepository.findByContaId(contaId);
//    }
//    public List<Transacao> buscaPorCategoria(Long categoriaId){
//        return transacaoRepository.findByCategoriaId(categoriaId);
//    }
//
//    public List<Transacao> listarTransacoesPorData(Long contaId){
//        return transacaoRepository.findByContaIdOrderByDataDesc(contaId);
//    }
//    public List<Transacao> listarTransacoesPorCategoriaEValor(Long contaId, Long categoriaId) {
//        return transacaoRepository.findByContaIdAndCategoriaIdOrderByValorDesc(contaId, categoriaId);
//    }
//    public List<Transacao> listarTransacoesValorEspecifico(Long contaId, BigDecimal valor){
//        return transacaoRepository.findByContaIdAndValorGreaterThanEqual(contaId, valor);
//    }
//
//    private TransacaoRequestDTO converterParaRequestDto(Transacao t) {
//        return new TransacaoRequestDTO(
//                t.getDescricao(),
//                t.getValor(),
//                t.getTipo(),
//                t.getConta().getId(),
//                t.getCategoria().getId()
//        );
//    }
//
}
