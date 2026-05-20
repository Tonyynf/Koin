package com.project.services;

import com.project.dto.ContaRequestDTO;
import com.project.dto.ContaResponseDTO;
import com.project.exceptions.DuplicateResourceException;
import com.project.exceptions.ResourceNotFoundException;
import com.project.models.Conta;
import org.junit.jupiter.api.Test;
import com.project.repositories.ContaRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ContaServiceTest {
        @Mock
        private ContaRepository contaRepository;

        @InjectMocks
        private ContaService contaService;

        @Test
        void deveCriarContaComSucesso() {
            ContaRequestDTO request = new ContaRequestDTO("NuBank", new BigDecimal("1000.00"));
            Conta contaSalva = new Conta(1L, "NuBank", new BigDecimal("1000.00"));

            when(contaRepository.existsByNome("NuBank")).thenReturn(false);
            when(contaRepository.save(any())).thenReturn(contaSalva);


            ContaResponseDTO resultado = contaService.createConta(request);

            assertEquals("NuBank", resultado.nome());
            verify(contaRepository, times(1)).save(any());
        }

        @Test
        void deveLancarExceptionQuandoContaDuplicada() {
            ContaRequestDTO request = new ContaRequestDTO("Bradesco", new BigDecimal("500.00"));
            when(contaRepository.existsByNome("Bradesco")).thenReturn(true);

            assertThrows(DuplicateResourceException.class, () -> {
                contaService.createConta(request);
            });

            verify(contaRepository, never()).save(any());
        }

    }
