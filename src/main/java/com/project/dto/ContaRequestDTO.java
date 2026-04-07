package com.project.dto;

import jakarta.validation.constraints.*;

public record ContaRequestDTO(
        @NotBlank(message = "O nome não pode ser nulo")
        String nome
) {}
