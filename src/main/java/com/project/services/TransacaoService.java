package com.project.services;

import com.project.dto.TransacaoRequestDTO;
import com.project.dto.TransacaoResponseDTO;
import com.project.models.Conta;
import com.project.models.TipoTransacao;
import com.project.models.Transacao;
import com.project.repositories.ContaRepository;
import com.project.repositories.TransacaoRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class TransacaoService {

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;

    public TransacaoService(ContaRepository contaRepository, TransacaoRepository transacaoRepository) {
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
    }

    public List<Transacao> buscarTransacoes(){
        return transacaoRepository.findAll();
    }
    public BigDecimal CalcularValorReal(Long contaId){
        Conta conta = contaRepository.findById(contaId).orElseThrow();

        BigDecimal entradas = transacaoRepository.somarPorTipoEConta(TipoTransacao.RECEITA, contaId);

        BigDecimal saidas = transacaoRepository.somarPorTipoEConta(TipoTransacao.DESPESA, contaId);

        BigDecimal totalEntradas = (entradas != null) ? entradas : BigDecimal.ZERO;
        BigDecimal totalSaidas = (saidas != null) ? saidas : BigDecimal.ZERO;

        return conta.getSaldoInicial().add(totalEntradas).subtract(totalSaidas);
    }

    public List<Transacao> buscaPorConta(Long contaId) {
        return transacaoRepository.findByContaId(contaId);
    }

    public List<Transacao> buscaPorCategoria(Long categoriaId){
        return transacaoRepository.findByCategoriaId(categoriaId);
    }

    public List<Transacao> listarTransacoesPorData(Long contaId){
        return transacaoRepository.findByContaIdAndOrderByDataDesc(contaId);
    }

    public List<Transacao> listarTransacoesPorCategoriaEValor(Long contaId, Long categoriaId) {
        return transacaoRepository.findByContaIdAndCategoriaIdOrderByValorDesc(contaId, categoriaId);
    }

    public List<Transacao> listarTransacoesValorEspecifico(Long contaId, BigDecimal valor){
        return transacaoRepository.findByContaIdAndValorGreaterThanEqual(contaId, valor);
    }

    private TransacaoRequestDTO converterParaRequestDto(Transacao t) {
        return new TransacaoRequestDTO(
                t.getDescricao(),
                t.getValor(),
                t.getTipo(),
                t.getConta().getId(),
                t.getCategoria().getId()
        );
    }

    private TransacaoResponseDTO converterParaResponseDto(Transacao t) {
        return new TransacaoResponseDTO(
                t.getId(),
                t.getDescricao(),
                t.getValor(),
                t.getTipo(),
                t.getData(),
                t.getCategoria().getNome(),
                t.getConta().getNome()
        );
    }

    public List<TransacaoResponseDTO> buscarExtrato(Long contaId, BigDecimal min) {
        List<Transacao> listaDoBanco;

        if (min == null) {
            // Busca simples por conta
            listaDoBanco = transacaoRepository.findByContaIdAndOrderByDataDesc(contaId);
        } else {
            // Busca com filtro de valor mínimo
            listaDoBanco = transacaoRepository.findByContaIdAndValorGreaterThanEqual(contaId, min);
        }

        // A mágica do Stream: transforma cada Transacao em TransacaoDTO
        return listaDoBanco.stream()
                .map(this::converterParaResponseDto)
                .toList();
    }

}
