package com.bortolanza.fleet.modules.vehicle.service;

import com.bortolanza.fleet.common.exceptions.BusinessException;
import com.bortolanza.fleet.common.exceptions.ConflictException;
import com.bortolanza.fleet.common.exceptions.ResourceNotFoundException;
import com.bortolanza.fleet.modules.vehicle.dto.in.VehicleRequestDTO;
import com.bortolanza.fleet.modules.vehicle.dto.out.VehicleResponseDTO;
import com.bortolanza.fleet.modules.vehicle.entity.Vehicle;
import com.bortolanza.fleet.modules.vehicle.mapper.VehicleMapper;
import com.bortolanza.fleet.modules.vehicle.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleMapper vehicleMapper;

    @InjectMocks
    private VehicleService vehicleService;

    // TODO: Implement tests

    @Test
    void shouldCreateVehicleSuccessfully() {
        VehicleRequestDTO request = new VehicleRequestDTO();
        request.setPlate("abc-1234");
        Vehicle vehicle = new Vehicle();
        Vehicle savedVehicle = new Vehicle();
        savedVehicle.setPlate("ABC1234");
        VehicleResponseDTO response = new VehicleResponseDTO();

        when(vehicleRepository.existsByPlate("ABC1234")).thenReturn(false);
        when(vehicleMapper.toEntity(request)).thenReturn(vehicle);
        when(vehicleRepository.save(vehicle)).thenReturn(savedVehicle);
        when(vehicleMapper.toResponseDTO(savedVehicle)).thenReturn(response);

        VehicleResponseDTO result = vehicleService.createVehicle(request);

        assertNotNull(result);
        verify(vehicleRepository).existsByPlate("ABC1234");
        verify(vehicleRepository).save(vehicle);
        assertEquals("ABC1234", vehicle.getPlate());
    }

    @Test
    void shouldThrowConflictExceptionWhenPlateAlreadyExists() {
        VehicleRequestDTO request = new VehicleRequestDTO();
        request.setPlate("abc-1234");

        when(vehicleRepository.existsByPlate("ABC1234")).thenReturn(true);

        assertThrows(ConflictException.class, () -> vehicleService.createVehicle(request));
        verify(vehicleRepository, never()).save(any());
        verify(vehicleMapper, never()).toEntity(any());
    }

    @Test
    void shouldReturnVehicleResponseDTOWhenVehicleExistsById() {
        Long id = 1L;
        Vehicle vehicle = new Vehicle();
        VehicleResponseDTO response = new VehicleResponseDTO();

        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicle));
        when(vehicleMapper.toResponseDTO(vehicle)).thenReturn(response);

        VehicleResponseDTO result = vehicleService.findById(id);

        assertNotNull(result);
        assertEquals(response, result);
        verify(vehicleMapper).toResponseDTO(vehicle);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenVehicleDoesNotExistById() {
        Long id = 1L;

        when(vehicleRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> vehicleService.findById(id));
        verify(vehicleMapper, never()).toResponseDTO(any());
    }

    @Test
    void shouldReturnVehicleResponseDTOWhenVehicleExistsByPlate() {
        String plate = "abc-1234";
        String normalizedPlate = "ABC1234";
        Vehicle vehicle = new Vehicle();
        VehicleResponseDTO response = new VehicleResponseDTO();

        when(vehicleRepository.findByPlate(normalizedPlate)).thenReturn(Optional.of(vehicle));
        when(vehicleMapper.toResponseDTO(vehicle)).thenReturn(response);

        VehicleResponseDTO result = vehicleService.findByPlate(plate);

        assertNotNull(result);
        assertEquals(response, result);
        verify(vehicleRepository).findByPlate(normalizedPlate);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenVehicleDoesNotExistByPlate() {
        String plate = "abc-1234";
        String normalizedPlate = "ABC1234";

        when(vehicleRepository.findByPlate(normalizedPlate)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> vehicleService.findByPlate(plate));
    }

    @Test
    void shouldReturnAllVehicles() {
        List<Vehicle> vehicles = List.of(new Vehicle(), new Vehicle());
        List<VehicleResponseDTO> responses = List.of(new VehicleResponseDTO(), new VehicleResponseDTO());

        when(vehicleRepository.findAll()).thenReturn(vehicles);
        when(vehicleMapper.toResponseDTO(any(Vehicle.class))).thenReturn(responses.get(0), responses.get(1));

        List<VehicleResponseDTO> result = vehicleService.findAll();

        assertEquals(2, result.size());
        verify(vehicleMapper, times(2)).toResponseDTO(any(Vehicle.class));
    }

    @Test
    void shouldReturnEmptyListWhenNoVehiclesFound() {
        when(vehicleRepository.findAll()).thenReturn(List.of());

        List<VehicleResponseDTO> result = vehicleService.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldUpdateVehicleSuccessfully() {
        Long id = 1L;
        VehicleRequestDTO request = new VehicleRequestDTO();
        request.setPlate("new-plate");
        Vehicle vehicle = new Vehicle();
        vehicle.setPlate("old-plate");
        VehicleResponseDTO response = new VehicleResponseDTO();

        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.existsByPlate("NEWPLATE")).thenReturn(false);
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        when(vehicleMapper.toResponseDTO(vehicle)).thenReturn(response);

        VehicleResponseDTO result = vehicleService.updateVehicle(id, request);

        assertNotNull(result);
        assertEquals("NEWPLATE", vehicle.getPlate());
        verify(vehicleMapper).updateEntity(request, vehicle);
        verify(vehicleRepository).save(vehicle);
    }

    @Test
    void shouldNotCheckDuplicatePlateWhenPlateIsNotChanged() {
        Long id = 1L;
        VehicleRequestDTO request = new VehicleRequestDTO();
        request.setPlate("ABC1234");
        Vehicle vehicle = new Vehicle();
        vehicle.setPlate("ABC1234");
        VehicleResponseDTO response = new VehicleResponseDTO();

        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        when(vehicleMapper.toResponseDTO(vehicle)).thenReturn(response);

        vehicleService.updateVehicle(id, request);

        verify(vehicleRepository, never()).existsByPlate(anyString());
        verify(vehicleRepository).save(vehicle);
    }

    @Test
    void shouldReturnVehicleForMaintenanceWhenPlateExists() {
        String plate = "abc-1234";
        String normalizedPlate = "ABC1234";
        Vehicle vehicle = new Vehicle();

        when(vehicleRepository.findByPlate(normalizedPlate)).thenReturn(Optional.of(vehicle));

        Vehicle result = vehicleService.getAndValidateForMaintenance(plate);

        assertNotNull(result);
        assertEquals(vehicle, result);
        verify(vehicleRepository).findByPlate(normalizedPlate);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionForMaintenanceWhenVehicleNotFound() {
        String plate = "abc-1234";
        String normalizedPlate = "ABC1234";

        when(vehicleRepository.findByPlate(normalizedPlate)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> vehicleService.getAndValidateForMaintenance(plate));
    }

    @Test
    void shouldUpdateMileageSuccessfully() {
        Long id = 1L;
        Long currentMileage = 100000L;
        Long newMileage = 120000L;
        Vehicle vehicle = new Vehicle();
        vehicle.setCurrentMileage(currentMileage);

        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicle));

        vehicleService.updateMileage(id, newMileage);

        assertEquals(newMileage, vehicle.getCurrentMileage());
        verify(vehicleRepository).save(vehicle);
    }

    @Test
    void shouldThrowBusinessExceptionWhenNewMileageIsLowerThanCurrent() {
        Long id = 1L;
        Long currentMileage = 100000L;
        Long newMileage = 90000L;
        Vehicle vehicle = new Vehicle();
        vehicle.setCurrentMileage(currentMileage);

        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicle));

        assertThrows(BusinessException.class, () -> vehicleService.updateMileage(id, newMileage));
        verify(vehicleRepository, never()).save(any());
    }
}
