package com.bortolanza.fleet.modules.auth.config;

import com.bortolanza.fleet.modules.user.dto.in.UserRequestDTO;
import com.bortolanza.fleet.modules.user.enums.UserRole;
import com.bortolanza.fleet.modules.user.repository.UserRepository;
import com.bortolanza.fleet.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("test")
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public void run(String... args) {

        String adminEmail = "admin@mbfleet.com.br";

        System.out.println("Executando DataInitializer do perfil test...");

        if (userRepository.existsByEmail(adminEmail)) {
            System.out.println("Usuário padrão já existe.");
            return;
        }

        UserRequestDTO admin = UserRequestDTO.builder()
                .name("Administrador")
                .email(adminEmail)
                .password("Admin@123456")
                .role(UserRole.ADMIN)
                .companyId(null)
                .build();

        userService.createUser(admin);

        System.out.println(
                "Usuário padrão de teste criado: " + adminEmail
        );
    }
}
