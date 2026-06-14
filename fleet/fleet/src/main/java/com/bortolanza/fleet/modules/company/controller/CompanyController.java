package com.bortolanza.fleet.modules.controller;

import com.bortolanza.fleet.modules.dto.in.CompanyRequestDTO;
import com.bortolanza.fleet.modules.dto.out.CompanyResponseDTO;
import com.bortolanza.fleet.modules.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyResponseDTO>createCompany(@RequestBody CompanyRequestDTO dto) {
        CompanyResponseDTO response = companyService.createCompany(dto);
        return ResponseEntity.ok(response);
    }
}
