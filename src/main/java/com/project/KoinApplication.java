package com.project;

import com.project.models.*;
import com.project.repositories.*;
import com.project.services.TransacaoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@SpringBootApplication
public class KoinApplication {

	public static void main(String[] args) {
		SpringApplication.run(KoinApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(ContaRepository contaRepo,
								  TransacaoRepository transacaoRepo,
								  TransacaoService service){
		return args -> {
			Conta minhaConta = new Conta(null, "Carteira", new BigDecimal("100.00"));
			contaRepo.save(minhaConta);

			Transacao r1 = new Transacao(null, "Salário", new BigDecimal("50.00"), LocalDateTime.now(), TipoTransacao.RECEITA, minhaConta, null);

			Transacao d1 = new Transacao(null, "Hamburguer", new BigDecimal("20.00"), LocalDateTime.now(), TipoTransacao.DESPESA, minhaConta, null);

			BigDecimal saldoFinal = service.CalcularValorReal(minhaConta.getId());

			System.out.println("\n--- TESTE DO KOIN APPLICATION ---");
			System.out.println("Saldo Inicial: R$ 100.00");
			System.out.println("Transações: +50.00 e -20.00");
			System.out.println("RESULTADO DO SERVICE: R$ " + saldoFinal);
			System.out.println("----------------------------------\n");
		};
	}

}
