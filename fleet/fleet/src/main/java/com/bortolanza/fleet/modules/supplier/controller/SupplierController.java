package com.bortolanza.fleet.modules.supplier.controller;

import com.bortolanza.fleet.modules.supplier.dto.in.SupplierRequestDTO;
import com.bortolanza.fleet.modules.supplier.dto.out.SupplierResponseDTO;
import com.bortolanza.fleet.modules.supplier.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<SupplierResponseDTO>> findAllSupplier() {
        List<SupplierResponseDTO> supplier = supplierService.findAll();
        return ResponseEntity.ok(supplier);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SupplierResponseDTO> findSupplierById(@PathVariable Long id) {
        SupplierResponseDTO supplier = supplierService.findById(id);
        return ResponseEntity.ok(supplier);
    }

    @GetMapping(value = "/cnpj/{cnpj}")
    public ResponseEntity<SupplierResponseDTO> findSupplierByCnpj(@PathVariable String cnpj) {
        SupplierResponseDTO supplier = supplierService.findByCnpj(cnpj);
        return ResponseEntity.ok(supplier);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<SupplierResponseDTO> findSupplierByName(@RequestParam String name) {
        SupplierResponseDTO supplier = supplierService.findByName(name);
        return ResponseEntity.ok(supplier);
    }

    @PostMapping
    public ResponseEntity<SupplierResponseDTO> createSupplier(@Valid @RequestBody SupplierRequestDTO dto) {
        SupplierResponseDTO response = supplierService.createSupllier(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value ="/{id}")
    public ResponseEntity<SupplierResponseDTO> updateSupplier(@PathVariable Long id, @RequestBody SupplierRequestDTO dto) {
        SupplierResponseDTO supplier = supplierService.updateSupplier(id, dto);
        return ResponseEntity.ok(supplier);
    }
}
