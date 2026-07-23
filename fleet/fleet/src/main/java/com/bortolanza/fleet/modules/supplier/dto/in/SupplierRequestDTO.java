package com.bortolanza.fleet.modules.supplier.dto.in;

import com.bortolanza.fleet.modules.company.entity.Company;
import com.bortolanza.fleet.modules.supplier.enums.SupplierType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierRequestDTO {

    @NotNull(message = "O ID da empresa é obrigatório")
    private Long companyId;

    @NotBlank(message = "O nome do fornecedor é obrigatório")
    @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres")
    private String name;

    @Size(max = 14, message = "O CNPJ deve ter no máximo 14 caracteres")
    private String cnpj;

    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
    private String phone;

    @Email(message = "E-mail inválido")
    @Size(max = 180, message = "O e-mail deve ter no máximo 180 caracteres")
    private String email;

    @Size(max = 150, message = "O nome do contato deve ter no máximo 150 caracteres")
    private String contactName;

    @NotNull(message = "O tipo do fornecedor é obrigatório")
    private SupplierType supplierType;

    @Size(max = 150, message = "O endereço deve ter no máximo 150 caracteres")
    private String address;

    @Size(max = 150, message = "A cidade deve ter no máximo 150 caracteres")
    private String city;

    @Size(max = 2, message = "O estado deve ter no máximo 2 caracteres")
    private String state;

    @Size(max = 9, message = "O CEP deve ter no máximo 9 caracteres")
    private String zipCode;

    @Size(max = 1000, message = "As observações devem ter no máximo 1000 caracteres")
    private String notes;
}
