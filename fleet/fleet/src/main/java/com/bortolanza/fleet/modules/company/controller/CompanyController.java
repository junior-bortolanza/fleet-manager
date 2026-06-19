package com.bortolanza.fleet.modules.company.controller;

import com.bortolanza.fleet.modules.company.dto.in.CompanyRequestDTO;
import com.bortolanza.fleet.modules.company.dto.out.CompanyResponseDTO;
import com.bortolanza.fleet.modules.company.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyResponseDTO>> findAllCompany() {
        List<CompanyResponseDTO> companies = companyService.findAll();
        return ResponseEntity.ok(companies);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyResponseDTO> findCompanyById(@PathVariable UUID id) {
        CompanyResponseDTO company = companyService.findById(id);
        return ResponseEntity.ok(company);
    }

    @GetMapping(value = "/cnpj/{cnpj}")
    public ResponseEntity<CompanyResponseDTO> findByCnpj(@PathVariable String cnpj) {
        CompanyResponseDTO company = companyService.findByCnpj(cnpj);
        return  ResponseEntity.ok(company);
    }

    @GetMapping(value = "/search")
    public  ResponseEntity<CompanyResponseDTO> findByName(@RequestParam String name) {
        CompanyResponseDTO company = companyService.findByName(name);
        return ResponseEntity.ok(company);
    }

    @PostMapping
    public ResponseEntity<CompanyResponseDTO> createCompany(@Valid @RequestBody CompanyRequestDTO dto) {
        CompanyResponseDTO response = companyService.createCompany(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CompanyResponseDTO> updateCompany(@PathVariable UUID id, @Valid @RequestBody CompanyRequestDTO dto) {
        CompanyResponseDTO company = companyService.updateCompany(id, dto);
        return  ResponseEntity.ok(company);
    }

}
