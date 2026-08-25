package com.bortolanza.fleet.modules.user.dto.in;

import com.bortolanza.fleet.modules.user.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String name;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Informe um e-mail válido")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(
            min = 8,
            message = "A senha deve possuir pelo menos 8 caracteres"
    )
    private String password;

    @NotNull(message = "O perfil do usuário é obrigatório")
    private UserRole role;

    private Long companyId;
}
