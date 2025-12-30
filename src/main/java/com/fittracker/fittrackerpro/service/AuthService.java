package com.fittracker.fittrackerpro.service;

import com.fittracker.fittrackerpro.dto.auth.request.PasswordChangeRequestDTO;
import com.fittracker.fittrackerpro.dto.auth.request.PasswordResetRequestDTO;
import com.fittracker.fittrackerpro.dto.auth.response.AuthResponseDTO;
import com.fittracker.fittrackerpro.dto.auth.request.LoginRequestDTO;
import com.fittracker.fittrackerpro.dto.auth.request.RegisterRequestDTO;
import com.fittracker.fittrackerpro.dto.auth.response.PasswordResetResponseDTO;
import com.fittracker.fittrackerpro.entity.PasswordResetToken;
import com.fittracker.fittrackerpro.entity.enums.Role;
import com.fittracker.fittrackerpro.repository.PasswordResetTokenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fittracker.fittrackerpro.entity.Usuario;
import com.fittracker.fittrackerpro.repository.UsuarioRepository;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService  {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final PasswordResetTokenRepository tokenRepository;

    public AuthService(UsuarioRepository usuarioRepository, JwtService jwtService,
                       BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                       PasswordResetTokenRepository tokenRepository) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
    }

    public boolean existsByEmail(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }

    public AuthResponseDTO saveUsuario(RegisterRequestDTO dto) {
        if (existsByEmail(dto.email())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email já cadastrado"
            );
        }

        Usuario entity = Usuario.builder()
                .nome(dto.nome())
                .email(dto.email())
                .senha(passwordEncoder.encode(dto.senha()))
                .role(Role.USER)
                .build();

        Usuario saved = usuarioRepository.save(entity);

        UserDetails userDetails =
                new org.springframework.security.core.userdetails.User(
                        saved.getEmail(),
                        saved.getSenha(),
                        List.of(new SimpleGrantedAuthority("ROLE_" + saved.getRole().name()))
                );

        String token = jwtService.generateToken(userDetails);

        return new AuthResponseDTO(token);
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.email(), dto.senha()));

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails);
            return new AuthResponseDTO(token);

        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas");
        }
    }

    //  Gera o token e o retorna (para ser exibido na API)
    public PasswordResetResponseDTO solicitarRedefinicaoSenha(PasswordResetRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        // apaga o token antigo, para gerar um novo
        tokenRepository.findByUsuario(usuario).ifPresent(tokenRepository::delete);

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .usuario(usuario)
                .expiryDate(LocalDateTime.now().plusMinutes(30))
                .build();

        tokenRepository.save(resetToken);

        return new PasswordResetResponseDTO(token);
    }

    // Recebe o token e a nova senha para efetivar a troca
    public void redefinirSenha(PasswordChangeRequestDTO dto) {
        PasswordResetToken resetToken = tokenRepository.findByToken(dto.token())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token inválido"));

        if (resetToken.isExpired()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token expirado. Solicite um novo.");
        }

        Usuario usuario = resetToken.getUsuario();
        usuario.setSenha(passwordEncoder.encode(dto.novaSenha())); // Criptografa a nova senha
        usuarioRepository.save(usuario);
        tokenRepository.delete(resetToken);
    }
}