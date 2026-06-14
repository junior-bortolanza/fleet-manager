package com.bortolanza.fleet.modules.company.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyResponseDTO {

    private UUID id;
    private String name;
    private String cnpj;
    private String address;
    private String email;
    private String phone;
    private String city;
    private String state;
    private String zipCode;
    private boolean active;
}
