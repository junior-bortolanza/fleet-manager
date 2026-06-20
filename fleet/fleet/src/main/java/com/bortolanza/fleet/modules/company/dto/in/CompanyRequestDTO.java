package com.bortolanza.fleet.modules.company.dto.in;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyRequestDTO {
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @NotBlank(message = "Nome fantasia é obrigatório")
    private String tradeName;

    @NotBlank(message = "CNPJ é obrigatório")
    private String cnpj;

    @NotBlank(message = "Telefone é obrigatório")
    private String phone;

    private String address;
    private String email;
    private String city;
    private String state;
    private String zipCode;
}
