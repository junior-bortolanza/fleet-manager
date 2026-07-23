package com.bortolanza.fleet.modules.vehicle.service;

import com.bortolanza.fleet.common.exceptions.BusinessException;
import com.bortolanza.fleet.common.exceptions.ConflictException;
import com.bortolanza.fleet.common.exceptions.ResourceNotFoundException;
import com.bortolanza.fleet.modules.vehicle.dto.in.VehicleRequestDTO;
import com.bortolanza.fleet.modules.vehicle.dto.out.VehicleResponseDTO;
import com.bortolanza.fleet.modules.vehicle.entity.Vehicle;
import com.bortolanza.fleet.modules.vehicle.mapper.VehicleMapper;
import com.bortolanza.fleet.modules.vehicle.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    @Transactional
    public VehicleResponseDTO createVehicle(VehicleRequestDTO vehicleRequestDTO) {
        String plate = normalizePlate(vehicleRequestDTO.getPlate());

        if (vehicleRepository.existsByPlate(plate)) {
            throw new ConflictException("Já existe um veículo cadastrado com a placa " + plate);
        }

        Vehicle vehicle = vehicleMapper.toEntity(vehicleRequestDTO);
        vehicle.setPlate(plate);

        vehicle = vehicleRepository.save(vehicle);
        return vehicleMapper.toResponseDTO(vehicle);
    }

    public VehicleResponseDTO findById(Long id) {
        return vehicleRepository.findById(id)
                .map(vehicleMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado!"));
    }

    public VehicleResponseDTO findByPlate(String plate) {
        String normalizedPlate = normalizePlate(plate);
        return vehicleRepository.findByPlate(normalizedPlate)
                .map(vehicleMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado!"));
    }

    public List<VehicleResponseDTO> findAll() {
        return vehicleRepository.findAll().stream()
                .map(vehicleMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public VehicleResponseDTO updateVehicle(Long id, VehicleRequestDTO vehicleRequestDTO) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado!"));

        String plate = normalizePlate(vehicleRequestDTO.getPlate());

        if (!vehicle.getPlate().equals(plate) && vehicleRepository.existsByPlate(plate)) {
            throw new ConflictException("Placa já cadastrada");
        }

        vehicleMapper.updateEntity(vehicleRequestDTO, vehicle);
        vehicle.setPlate(plate);
        return vehicleMapper.toResponseDTO(vehicleRepository.save(vehicle));
    }

    /**
     * Busca um veículo e valida se ele está apto para manutenção.
     * Uso inter-módulos.
     */
    public Vehicle getAndValidateForMaintenance(String plate) {
        String normalizedPlate = normalizePlate(plate);
        return vehicleRepository.findByPlate(normalizedPlate)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo com placa " + normalizedPlate + " não encontrado."));
    }

    @Transactional
    public void updateMileage(Long vehicleId, Long newMileage) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado."));
        
        if (newMileage < vehicle.getCurrentMileage()) {
            throw new BusinessException("A nova quilometragem (" + newMileage + ") não pode ser inferior à atual (" + vehicle.getCurrentMileage() + ").");
        }
        
        vehicle.setCurrentMileage(newMileage);
        vehicleRepository.save(vehicle);
    }

    private String normalizePlate(String plate) {
        if (plate == null) return null;
        return plate.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
    }
}
