package com.project.config;

import com.project.models.Categoria;
import com.project.models.Conta;
import com.project.models.TipoTransacao;
import com.project.models.Transacao;

import com.project.repositories.CategoriaRepository;
import com.project.repositories.ContaRepository;
import com.project.repositories.TransacaoRepository;

import com.project.services.TransacaoService;
import org.springframework.boot.CommandLineRunner;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

//@Configuration comentada para ser ignorada quando executar o KoinApplication
public class TestConfig {
    @Bean
    public CommandLineRunner run(CategoriaRepository catRepo,
                                 ContaRepository contaRepo,
                                 TransacaoRepository transRepo) {
        return args -> {
          Categoria c1 = new Categoria(null, "Alimentação", new BigDecimal("800.00"), "#FF5733");
          Categoria c2 = new Categoria(null, "Lazer", new BigDecimal("300.00"), "#33B5FF");
          catRepo.saveAll(Arrays.asList(c1, c2));

          Conta conta1 = new Conta(null, "Carteira Principal", new BigDecimal("500.00"));

          Transacao t1 = new Transacao(null, "Mercado", new BigDecimal("300.00"),
                  LocalDateTime.now(), TipoTransacao.DESPESA, conta1, c1);

          Transacao t2 = new Transacao(null, "FreeLance", new BigDecimal("300.00"),
                   LocalDateTime.now(), TipoTransacao.RECEITA, conta1, null);

          transRepo.saveAll(Arrays.asList(t1,t2));

          System.out.println("Banco de dados populados com sucesso!");
        };
    }
}
