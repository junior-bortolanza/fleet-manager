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

    @Test
    void shouldCreateVehicleSuccessfully() {
        VehicleRequestDTO request = new VehicleRequestDTO();
        request.setPlate("abc-1234");
        Vehicle vehicle = new Vehicle();
        vehicle.setPlate("ABC1234");
        Vehicle savedVehicle = new Vehicle();
        savedVehicle.setPlate("ABC1234");
        VehicleResponseDTO response = new VehicleResponseDTO();

        when(vehicleRepository.existsByPlate("ABC1234")).thenReturn(false);
        when(vehicleMapper.toEntity(request)).thenReturn(vehicle);
        when(vehicleRepository.save(vehicle)).thenReturn(savedVehicle);
        when(vehicleMapper.toResponseDTO(savedVehicle)).thenReturn(response);

        VehicleResponseDTO result = vehicleService.createVehicle(request);

        assertNotNull(result);
        assertEquals(response, result);
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
        verify(vehicleMapper, never()).toEntity(any(VehicleRequestDTO.class));
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
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(1L);
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setId(2L);
        List<Vehicle> vehicles = List.of(vehicle1, vehicle2);
        
        VehicleResponseDTO response1 = new VehicleResponseDTO();
        VehicleResponseDTO response2 = new VehicleResponseDTO();

        when(vehicleRepository.findAll()).thenReturn(vehicles);
        when(vehicleMapper.toResponseDTO(vehicle1)).thenReturn(response1);
        when(vehicleMapper.toResponseDTO(vehicle2)).thenReturn(response2);

        List<VehicleResponseDTO> result = vehicleService.findAll();

        assertEquals(2, result.size());
        assertEquals(response1, result.get(0));
        assertEquals(response2, result.get(1));
        verify(vehicleMapper).toResponseDTO(vehicle1);
        verify(vehicleMapper).toResponseDTO(vehicle2);
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
    void shouldThrowResourceNotFoundExceptionWhenUpdatingNonExistentVehicle() {
        Long id = 1L;
        VehicleRequestDTO request = new VehicleRequestDTO();

        when(vehicleRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> vehicleService.updateVehicle(id, request));
        verify(vehicleMapper, never()).updateEntity(any(), any());
        verify(vehicleRepository, never()).save(any());
    }

    @Test
    void shouldThrowConflictExceptionWhenUpdatingToAlreadyExistingPlate() {
        Long id = 1L;
        VehicleRequestDTO request = new VehicleRequestDTO();
        request.setPlate("new-plate");
        Vehicle vehicle = new Vehicle();
        vehicle.setPlate("old-plate");

        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.existsByPlate("NEWPLATE")).thenReturn(true);

        assertThrows(ConflictException.class, () -> vehicleService.updateVehicle(id, request));
        verify(vehicleMapper, never()).updateEntity(any(), any());
        verify(vehicleRepository, never()).save(any());
    }

    @Test
    void shouldUpdateVehicleSuccessfullyWhenPlateIsNotChanged() {
        Long id = 1L;
        VehicleRequestDTO request = new VehicleRequestDTO();
        request.setPlate("ABC1234");
        Vehicle vehicle = new Vehicle();
        vehicle.setPlate("ABC1234");
        VehicleResponseDTO response = new VehicleResponseDTO();

        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        when(vehicleMapper.toResponseDTO(vehicle)).thenReturn(response);

        VehicleResponseDTO result = vehicleService.updateVehicle(id, request);

        assertNotNull(result);
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

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
            () -> vehicleService.getAndValidateForMaintenance(plate));
        
        assertEquals("Veículo com placa " + normalizedPlate + " não encontrado.", exception.getMessage());
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
    void shouldUpdateMileageSuccessfullyWhenMileageIsTheSame() {
        Long id = 1L;
        Long currentMileage = 100000L;
        Long newMileage = 100000L;
        Vehicle vehicle = new Vehicle();
        vehicle.setCurrentMileage(currentMileage);

        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicle));

        vehicleService.updateMileage(id, newMileage);

        assertEquals(currentMileage, vehicle.getCurrentMileage());
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

    @Test
    void shouldThrowResourceNotFoundExceptionWhenUpdatingMileageOfNonExistentVehicle() {
        Long id = 1L;
        Long newMileage = 120000L;

        when(vehicleRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> vehicleService.updateMileage(id, newMileage));
        verify(vehicleRepository, never()).save(any());
    }
}
