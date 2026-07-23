package com.bortolanza.fleet.modules.maintenance.service;

import com.bortolanza.fleet.common.exceptions.BusinessException;
import com.bortolanza.fleet.modules.company.entity.Company;
import com.bortolanza.fleet.modules.driver.entity.Driver;
import com.bortolanza.fleet.modules.driver.service.DriverService;
import com.bortolanza.fleet.modules.maintenance.dto.in.MaintenanceRequestDTO;
import com.bortolanza.fleet.modules.maintenance.dto.out.MaintenanceResponseDTO;
import com.bortolanza.fleet.modules.maintenance.entity.Maintenance;
import com.bortolanza.fleet.modules.maintenance.mapper.MaintenanceMapper;
import com.bortolanza.fleet.modules.maintenance.repository.MaintenanceRepository;
import com.bortolanza.fleet.modules.supplier.service.SupplierService;
import com.bortolanza.fleet.modules.vehicle.entity.Vehicle;
import com.bortolanza.fleet.modules.vehicle.service.VehicleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MaintenanceServiceTest {

    @Mock
    private MaintenanceRepository maintenanceRepository;

    @Mock
    private VehicleService vehicleService;

    @Mock
    private DriverService driverService;

    @Mock
    private SupplierService supplierService;

    @Mock
    private MaintenanceMapper maintenanceMapper;

    @InjectMocks
    private MaintenanceService maintenanceService;

    @Test
    @DisplayName("Deve criar manutenção com sucesso quando todos os dados forem válidos")
    void createMaintenanceSuccess() {
        // Arrange
        MaintenanceRequestDTO dto = MaintenanceRequestDTO.builder()
                .plate("ABC1234")
                .odometerAtService(10000L)
                .build();

        Company company = Company.builder().id(1L).build();
        Vehicle vehicle = Vehicle.builder().id(1L).company(company).currentMileage(9000L).plate("ABC1234").build();

        when(vehicleService.getAndValidateForMaintenance("ABC1234")).thenReturn(vehicle);
        when(maintenanceMapper.toEntity(dto)).thenReturn(new Maintenance());
        when(maintenanceRepository.save(any(Maintenance.class))).thenAnswer(i -> i.getArguments()[0]);
        when(maintenanceMapper.toResponseDTO(any(Maintenance.class))).thenReturn(new MaintenanceResponseDTO());

        // Act
        MaintenanceResponseDTO result = maintenanceService.createMaintenance(dto);

        // Assert
        assertNotNull(result);
        verify(vehicleService).getAndValidateForMaintenance("ABC1234");
        verify(vehicleService).updateMileage(1L, 10000L);
        verify(maintenanceRepository).save(any(Maintenance.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o motorista pertence a uma empresa diferente do veículo")
    void createMaintenanceDriverDifferentCompany() {
        // Arrange
        MaintenanceRequestDTO dto = MaintenanceRequestDTO.builder()
                .plate("ABC1234")
                .driverId(10L)
                .build();

        Company companyA = Company.builder().id(1L).build();
        Company companyB = Company.builder().id(2L).build();

        Vehicle vehicle = Vehicle.builder().id(1L).company(companyA).build();
        Driver driver = Driver.builder().id(10L).company(companyB).build();

        when(vehicleService.getAndValidateForMaintenance("ABC1234")).thenReturn(vehicle);
        when(driverService.getById(10L)).thenReturn(driver);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> 
            maintenanceService.createMaintenance(dto)
        );

        assertEquals("O motorista não pertence à mesma empresa do veículo", exception.getMessage());
        verify(maintenanceRepository, never()).save(any());
    }
}