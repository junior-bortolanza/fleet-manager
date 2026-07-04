package com.bortolanza.fleet.modules.driver.dto;


import com.bortolanza.fleet.modules.driver.enums.CnhCategory;
import com.bortolanza.fleet.modules.driver.enums.DriverStatus;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverRequestDTO {


    @NotNull
    private String driveName;

    @NotNull
    private Long companyId;


    @NotNull
    @Size(min = 11, max = 11)
    @Pattern(
            regexp = "^(\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})$",
            message = "CPF deve conter 11 dígitos ou estar no formato 000.000.000-00"
    )
    private String cpf;

    @Size(min = 9, max = 20)
    @NotNull
    private String cnh;

    @NotNull
    private CnhCategory cnhCategory;


    @NotNull
    @Future
    private LocalDate cnhExpiration;

    @NotNull
    @Past
    private LocalDate birthDate;

    private DriverStatus driverStatus;
    private String moppNumber;
    private LocalDate moppExpirationDate;
    private String phone;
    private String email;
    private String notes;
}
