package com.bortolanza.fleet.modules.supplier.dto.out;

import com.bortolanza.fleet.modules.supplier.enums.SupplierType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierResponseDTO {

    private Long id;
    private Long companyId;
    private String name;
    private String cnpj;
    private String phone;
    private String email;
    private String contactName;
    private SupplierType supplierType;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String notes;
}
