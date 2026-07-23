package com.bortolanza.fleet.modules.driver.dto.in;

import com.bortolanza.fleet.modules.driver.enums.CnhCategory;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverRequestDTO {


    @NotNull(message = "Nome é obrigatório!")
    @Size(min = 3, max = 120)
    private String driveName;

    @NotNull(message = "Empresa é obrigatória!")
    private Long companyId;


    @NotNull(message = "CPF é obrigatório!")
    @Size(min = 11, max = 11)
    @Pattern(
            regexp = "^(\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})$",
            message = "CPF deve conter 11 dígitos ou estar no formato 000.000.000-00"
    )
    private String cpf;

    @Size(min = 9, max = 20)
    @NotNull(message = "CNH é obrigatória!")
    private String cnh;

    @NotNull(message = "Categoria da CNH é obrigatória!")
    private CnhCategory cnhCategory;


    @NotNull(message = "Vencimento da CNH é obrigatório")
    @Future(message = "CNH deve ter uma data de vencimento futura")
    private LocalDate cnhExpiration;

    private LocalDate moppExpirationDate;
    private String moppNumber;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve estar no passado")
    private LocalDate birthDate;

    @Size(max = 20)
    private String phone;
    @Email(message = "E-mail inválido")
    private String email;
    @Size(max = 1000)
    private String notes;




}
