package com.bortolanza.fleet.modules.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequestDTO {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
