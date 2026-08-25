package com.bortolanza.fleet.modules.maintenance.controller;

import com.bortolanza.fleet.modules.maintenance.dto.in.MaintenanceRequestDTO;
import com.bortolanza.fleet.modules.maintenance.dto.out.MaintenanceResponseDTO;
import com.bortolanza.fleet.modules.maintenance.enums.MaintenanceStatus;
import com.bortolanza.fleet.modules.maintenance.service.MaintenanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/maintenance")
@RequiredArgsConstructor
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @GetMapping
    public ResponseEntity<List<MaintenanceResponseDTO>> getAllMaintenances() {
        List<MaintenanceResponseDTO> maintenances = maintenanceService.findAllMaintenances();
        return  ResponseEntity.ok(maintenances);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceResponseDTO> getMaintenanceById(@PathVariable Long id) {
        MaintenanceResponseDTO maintenance = maintenanceService.findById(id);
        return ResponseEntity.ok(maintenance);

    }

    @GetMapping("/invoice/{invoice}")
    public ResponseEntity<MaintenanceResponseDTO> getMaintenanceByInvoice(@PathVariable String invoice) {
        MaintenanceResponseDTO maintenance = maintenanceService.findByInvoiceNumber(invoice);
        return  ResponseEntity.ok(maintenance);
    }

    @GetMapping("/plate/{plate}")
    public ResponseEntity<List<MaintenanceResponseDTO>> getMaintenanceByPlate(@PathVariable String plate) {
        List<MaintenanceResponseDTO> maintenance = maintenanceService.findByVehiclePlate(plate);
        return  ResponseEntity.ok(maintenance);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<MaintenanceResponseDTO>> getMaintenanceByStatus(@PathVariable MaintenanceStatus status) {
        return ResponseEntity.ok(maintenanceService.findByStatus(status));
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<MaintenanceResponseDTO>> getMaintenanceByVehicleId(@PathVariable Long vehicleId){
        return ResponseEntity.ok(maintenanceService.findByVehicleId(vehicleId));
    }

    @GetMapping("/compnay/{companyId}")
    public ResponseEntity<List<MaintenanceResponseDTO>> getMaintenanceByCompanyId(@PathVariable Long companyId){
        return ResponseEntity.ok(maintenanceService.findByCompanyId(companyId));
    }

    @PostMapping
    public ResponseEntity<MaintenanceResponseDTO> createMaintenance(@Valid @RequestBody MaintenanceRequestDTO dto) {
        MaintenanceResponseDTO maintenance = maintenanceService.createMaintenance(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(maintenance);
    }
}
