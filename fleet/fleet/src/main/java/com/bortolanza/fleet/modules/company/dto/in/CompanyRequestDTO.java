package com.bortolanza.fleet.modules.company.dto.in;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyRequestDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String tradeName;

    @NotBlank
    private String cnpj;

    private String address;
    private String email;
    private String phone;
    private String city;
    private String state;
    private String zipCode;
}
