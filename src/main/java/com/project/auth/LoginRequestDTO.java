package com.project.auth;

public record LoginRequestDTO(
        String email,
        String senha
) {}