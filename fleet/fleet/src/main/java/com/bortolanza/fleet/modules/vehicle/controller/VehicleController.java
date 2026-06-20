package com.bortolanza.fleet.modules.vehicle.controller;

import com.bortolanza.fleet.modules.vehicle.dto.in.VehicleRequestDTO;
import com.bortolanza.fleet.modules.vehicle.dto.out.VehicleResponseDTO;
import com.bortolanza.fleet.modules.vehicle.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<List<VehicleResponseDTO>> findAllVehicle() {
        List<VehicleResponseDTO> vehicles = vehicleService.findAll();
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<VehicleResponseDTO> findVehicleById(@PathVariable UUID id) {
        VehicleResponseDTO vehicles = vehicleService.findById(id);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/plate/{plate}")
    public ResponseEntity<VehicleResponseDTO> findVehicleByPlate(@PathVariable String plate) {
        VehicleResponseDTO vehicles = vehicleService.findByPlate(plate);
        return ResponseEntity.ok(vehicles);
    }

    @PostMapping
    public ResponseEntity<VehicleResponseDTO> saveVehicle(@Valid @RequestBody VehicleRequestDTO vehicleRequestDTO) {
        VehicleResponseDTO vehicle = vehicleService.createVehicle(vehicleRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicle);
    }

    @PutMapping(value ="/{id}" )
    public ResponseEntity<VehicleResponseDTO> updateVehicle(@PathVariable UUID id, @RequestBody VehicleRequestDTO vehicleRequestDTO) {
        System.out.println("TESTE NO CONTROLLER" );
        VehicleResponseDTO vehicle = vehicleService.updateVehicle(id, vehicleRequestDTO);
        return ResponseEntity.ok(vehicle);
    }

}
