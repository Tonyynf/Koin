package com.project.auth;

import com.project.models.Usuario;
import com.project.repositories.UsuarioRepository;
import com.project.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
    }

    public AuthResponseDTO register(RegisterRequestDTO dados) {
        if (usuarioRepository.findByEmail(dados.email()).isPresent()) {
            throw new RuntimeException("Email já cadastrado!");
        }

        Usuario usuario = new Usuario(
                null,
                dados.nome(),
                dados.email(),
                passwordEncoder.encode(dados.senha())
        );

        usuarioRepository.save(usuario);
        String token = jwtService.gerarToken(usuario.getEmail());
        return new AuthResponseDTO(token);
    }

    public AuthResponseDTO login(LoginRequestDTO dados) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dados.email(), dados.senha())
        );

        Usuario usuario = usuarioRepository.findByEmail(dados.email())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));

        String token = jwtService.gerarToken(usuario.getEmail());
        return new AuthResponseDTO(token);
    }
}