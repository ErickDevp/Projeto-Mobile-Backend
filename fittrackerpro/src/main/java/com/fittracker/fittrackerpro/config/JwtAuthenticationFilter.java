package com.fittracker.fittrackerpro.config; // Garanta que está no pacote 'config'

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired; // Usaremos @Autowired aqui para simplicidade
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fittracker.fittrackerpro.service.JwtService; // Importa o serviço JWT

@Component // Marca esta classe para ser gerenciada pelo Spring
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired // Injete o JwtService
    private JwtService jwtService;

    @Autowired // Injete o UserDetailsService (seu AuthService)
    private UserDetailsService userDetailsService;

    // Construtor vazio (necessário para @Autowired nos campos)
    public JwtAuthenticationFilter() {}

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain // O objeto que passa a requisição para o próximo filtro
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization"); // Pega o cabeçalho Authorization
        final String jwt;
        final String userEmail;

        // 1. Verifica se a rota é pública ou se o cabeçalho está ausente/incorreto
        if (request.getServletPath().contains("/auth") || authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Passa para o próximo filtro sem autenticar
            return;
        }

        // 2. Extrai o Token JWT (removendo o prefixo "Bearer ")
        jwt = authHeader.substring(7);
        try {
            userEmail = jwtService.extractUsername(jwt); // Usa o JwtService para ler o email do token

            // 3. Verifica se o email foi extraído e se o usuário ainda não está autenticado nesta requisição
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Carrega os detalhes do usuário usando o UserDetailsService (AuthService)
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                // 4. Valida o token (assinatura e expiração) usando o JwtService
                if (jwtService.isTokenValid(jwt, userDetails)) {

                    // 5. Cria o objeto de autenticação para o Spring Security
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null, // Credenciais (senha) não são necessárias aqui
                            userDetails.getAuthorities() // Permissões (vazio no nosso caso)
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request) // Adiciona detalhes da requisição
                    );

                    // 6. Define o usuário como autenticado no contexto de segurança do Spring
                    // Isso "loga" o usuário para esta requisição específica
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response); // Passa para o próximo filtro

        } catch (Exception e) {
            // Se houver qualquer erro na validação do token (expirado, inválido),
            // a requisição continua sem autenticação, e será bloqueada pelo SecurityConfig
            // com 403 Forbidden. Limpa o contexto para garantir.
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            // Poderíamos adicionar um log aqui para registrar a falha do token
        }
    }
}