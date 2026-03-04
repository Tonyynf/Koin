package com.project.services;

import com.project.models.Conta;
import com.project.models.TipoTransacao;
import com.project.repositories.ContaRepository;
import com.project.repositories.TransacaoRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class TransacaoService {

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;

    public TransacaoService(ContaRepository contaRepository, TransacaoRepository transacaoRepository) {
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
    }

    public BigDecimal CalcularValorReal(Long contaId){
        Conta conta = contaRepository.findById(contaId).orElseThrow();

        BigDecimal entradas = transacaoRepository.somarPorTipoEConta(TipoTransacao.RECEITA, contaId);

        BigDecimal saidas = transacaoRepository.somarPorTipoEConta(TipoTransacao.DESPESA, contaId);

        BigDecimal totalEntradas = (entradas != null) ? entradas : BigDecimal.ZERO;
        BigDecimal totalSaidas = (saidas != null) ? saidas : BigDecimal.ZERO;

        return conta.getSaldoInicial().add(totalEntradas).subtract(totalSaidas);
    }
}
