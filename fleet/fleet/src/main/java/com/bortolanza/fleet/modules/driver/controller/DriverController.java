package com.bortolanza.fleet.modules.driver.controller;

import com.bortolanza.fleet.modules.driver.dto.in.DriverRequestDTO;
import com.bortolanza.fleet.modules.driver.dto.out.DriverResponseDTO;
import com.bortolanza.fleet.modules.driver.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @GetMapping
    public ResponseEntity<Page<DriverResponseDTO>> findAllDriversPaged(Pageable pageable) {
        Page<DriverResponseDTO> drivers = driverService.findAllPaged(pageable);
        return ResponseEntity.ok(drivers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> findDriverById(@PathVariable Long id) {
        DriverResponseDTO driver = driverService.findById(id);
        return ResponseEntity.ok(driver);
    }

    @GetMapping("/cnh/{cnh}")
    public ResponseEntity<DriverResponseDTO> findDriverBYCnh(@PathVariable String cnh) {
        DriverResponseDTO driver = driverService.findByCnh(cnh);
        return ResponseEntity.ok(driver);
    }

    @PostMapping
    public ResponseEntity<DriverResponseDTO> createDriver(@Valid @RequestBody DriverRequestDTO dto) {
        DriverResponseDTO driver = driverService.createDriver(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(driver);
    }
    @PutMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> updateDriver(@PathVariable Long id, @RequestBody DriverRequestDTO dto) {
        DriverResponseDTO driver = driverService.updateDriver(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(driver);
    }
}
