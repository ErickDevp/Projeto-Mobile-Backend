package com.fittracker.fittrackerpro.config;

import com.fittracker.fittrackerpro.entity.Usuario;
import com.fittracker.fittrackerpro.entity.enums.Role;
import com.fittracker.fittrackerpro.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (usuarioRepository.existsByRole(Role.ADMIN)) {
            return; // já existe admin, não faz nada
        }

        Usuario admin = Usuario.builder()
                .nome("Administrador")
                .email("admin@fittracker.com")
                .senha(passwordEncoder.encode("admin123"))
                .role(Role.ADMIN)
                .diasAtivo(0)
                .totalTreinos(0)
                .build();

        usuarioRepository.save(admin);

        System.out.println("🔥 ADMIN criado com sucesso:");
        System.out.println("📧 email: admin@fittracker.com");
        System.out.println("🔑 senha: admin123");
    }
}
