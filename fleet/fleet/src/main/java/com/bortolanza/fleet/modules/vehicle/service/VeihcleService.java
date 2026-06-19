package com.bortolanza.fleet.modules.vehicle.service;

import com.bortolanza.fleet.common.exceptions.BusinessException;
import com.bortolanza.fleet.modules.vehicle.dto.in.VehicleRequestDTO;
import com.bortolanza.fleet.modules.vehicle.dto.out.VehicleResponseDTO;
import com.bortolanza.fleet.modules.vehicle.entity.Vehicle;
import com.bortolanza.fleet.modules.vehicle.mapper.VehicleMapperImpl;
import com.bortolanza.fleet.modules.vehicle.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class VeihcleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapperImpl vehicleMapper;

    public VehicleResponseDTO createVehicle(VehicleRequestDTO vehicleRequestDTO) {
        String plate = vehicleRequestDTO.getPlate();

        if(vehicleRepository.existsByPlate(plate)){
            throw new BusinessException("Vehicle already exists");
        }

        Vehicle vehicle = vehicleMapper.toEntity(vehicleRequestDTO);
        vehicle.setPlate(plate);

        vehicle = vehicleRepository.save(vehicle);
        return  vehicleMapper.toResponseDTO(vehicle);
    }

    public VehicleResponseDTO findById(UUID id) {
        return vehicleRepository.findById(id).map(vehicleMapper::toResponseDTO)
                .orElseThrow(() -> new BusinessException("Vehicle not found"));
    }

    public VehicleResponseDTO findByPlate(String plate) {

        return vehicleRepository.findByPlate(plate)
                .map(vehicleMapper::toResponseDTO)
                .orElseThrow(() -> new BusinessException("Veiculo nao encontrado"));
    }

    @Transactional
    public VehicleResponseDTO updateVehicle(UUID id, VehicleRequestDTO vehicleRequestDTO) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Vehicle not found"));

        String plate = vehicleRequestDTO.getPlate();

        if(!vehicle.getPlate().equals(plate) && vehicleRepository.existsByPlate(plate)){
            throw new BusinessException("Placa ja cadastrada");

        }

        vehicleMapper.toEntity(vehicleRequestDTO, vehicle);


        return vehicleMapper.toResponseDTO(vehicleRepository.save(vehicle));

    }

}
