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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.DoNotMock;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {
    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private TransacaoService transacaoService;

    @Test
    void deveCriarTransacaoComSucesso(){
        Conta contaTeste = new Conta(1L, "NuBank", new BigDecimal("1000.00"));
        Categoria categoriaTeste = new Categoria(1L, "Alimentação", new BigDecimal("500.00"), "#FF0000");

        // Arrange
        TransacaoRequestDTO request = new TransacaoRequestDTO("Mercado", new BigDecimal("500.00"), TipoTransacao.DESPESA, 1L, 1L);
        Transacao transacaoSalva = new Transacao(1L, "Mercado", new BigDecimal("500.00"), LocalDateTime.now(),  TipoTransacao.DESPESA, contaTeste, categoriaTeste);

        when(contaRepository.findById(1L)).thenReturn(Optional.of(contaTeste));
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoriaTeste));
        when(transacaoRepository.save(any())).thenReturn(transacaoSalva);
        // Act
        TransacaoResponseDTO resultado = transacaoService.createTransacao(request);

        // Assert
        assertEquals("Mercado", resultado.descricao());
        verify(transacaoRepository, times(1)).save(any());
    }    


}
