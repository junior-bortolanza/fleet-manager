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
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    public VehicleResponseDTO createVehicle(VehicleRequestDTO vehicleRequestDTO) {
        String plate = vehicleRequestDTO.getPlate();

        if(vehicleRepository.existsByPlate(plate)){
            throw new ConflictException("Vehicle already exists");
        }

        Vehicle vehicle = vehicleMapper.toEntity(vehicleRequestDTO);
        System.out.println("DTO mileage: " + vehicleRequestDTO.getCurrentMileage());
        System.out.println("Entity mileage: " + vehicle.getCurrentMileage());

        vehicle = vehicleRepository.save(vehicle);
        return  vehicleMapper.toResponseDTO(vehicle);
    }

    public VehicleResponseDTO findById(UUID id) {
        return vehicleRepository.findById(id).map(vehicleMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado!"));
    }

    public VehicleResponseDTO findByPlate(String plate) {

        return vehicleRepository.findByPlate(plate)
                .map(vehicleMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado!"));
    }

    public List<VehicleResponseDTO> findAll() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicles.stream()
                .map(vehicleMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public VehicleResponseDTO updateVehicle(UUID id, VehicleRequestDTO vehicleRequestDTO) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado!"));

        String plate = vehicleRequestDTO.getPlate().trim();

        if(!vehicle.getPlate().equals(plate) && vehicleRepository.existsByPlate(plate)){
            throw new ConflictException("Placa ja cadastrada");

        }

        vehicleMapper.updateEntity(vehicleRequestDTO, vehicle);
        vehicle.setPlate(plate);
        return vehicleMapper.toResponseDTO(vehicleRepository.save(vehicle));

    }

}
