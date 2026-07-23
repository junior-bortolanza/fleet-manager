package com.bortolanza.fleet.modules.driver.dto.out;

import com.bortolanza.fleet.modules.driver.enums.CnhCategory;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverResponseDTO {

    private Long id;
    private String driveName;
    private Long companyId;
    private String cpf;
    private String cnh;
    private CnhCategory cnhCategory;
    private LocalDate cnhExpiration;
    private LocalDate moppExpirationDate;
    private String moppNumber;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private String notes;
}
