package com.project.services;

import com.project.dto.CategoriaRequestDTO;
import com.project.dto.CategoriaResponseDTO;
import com.project.exceptions.DuplicateResourceException;
import com.project.exceptions.ResourceNotFoundException;
import com.project.models.Categoria;
import org.junit.jupiter.api.Test;
import com.project.repositories.CategoriaRepository;
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
class CategoriaServiceTest {
        @Mock
        private CategoriaRepository categoriaRepository;

        @InjectMocks
        private CategoriaService categoriaService;

    @Test
    void deveCriarCategoriaComSucesso() {
        // Arrange
        CategoriaRequestDTO request = new CategoriaRequestDTO("Alimentação", new BigDecimal("500.00"), "#FF0000");
        Categoria categoriaSalva = new Categoria(1L, "Alimentação", new BigDecimal("500.00"), "#FF0000");

        when(categoriaRepository.existsByNome("Alimentação")).thenReturn(false);
        when(categoriaRepository.save(any())).thenReturn(categoriaSalva);

        // Act
        CategoriaResponseDTO resultado = categoriaService.createCategoria(request);

        // Assert
        assertEquals("Alimentação", resultado.nome());
        verify(categoriaRepository, times(1)).save(any());
    }

    @Test
    void deveLancarExceptionQuandoCategoriaDuplicada() {
        // Arrange
        CategoriaRequestDTO request = new CategoriaRequestDTO("Geral", new BigDecimal("1000.00"), "#000");
        when(categoriaRepository.existsByNome("Geral")).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateResourceException.class, () -> {
            categoriaService.createCategoria(request);
        });

        verify(categoriaRepository, never()).save(any());
    }
}


