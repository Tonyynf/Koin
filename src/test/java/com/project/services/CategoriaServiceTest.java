package com.project.services;

import com.project.dto.CategoriaRequestDTO;
import com.project.dto.CategoriaResponseDTO;
import com.project.exceptions.DuplicateResourceException;
import com.project.exceptions.ResourceNotFoundException;
import com.project.models.Categoria;
import com.project.repositories.CategoriaRepository;
import org.junit.jupiter.api.Test;
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
    }

