package com.bortolanza.fleet.modules.maintenance.service;

import com.bortolanza.fleet.common.exceptions.BusinessException;
import com.bortolanza.fleet.common.exceptions.ResourceNotFoundException;
import com.bortolanza.fleet.modules.driver.entity.Driver;
import com.bortolanza.fleet.modules.driver.service.DriverService;
import com.bortolanza.fleet.modules.maintenance.dto.in.MaintenanceRequestDTO;
import com.bortolanza.fleet.modules.maintenance.dto.out.MaintenanceResponseDTO;
import com.bortolanza.fleet.modules.maintenance.entity.Maintenance;
import com.bortolanza.fleet.modules.maintenance.enums.MaintenanceStatus;
import com.bortolanza.fleet.modules.maintenance.mapper.MaintenanceMapper;
import com.bortolanza.fleet.modules.maintenance.repository.MaintenanceRepository;
import com.bortolanza.fleet.modules.supplier.entity.Supplier;
import com.bortolanza.fleet.modules.supplier.service.SupplierService;
import com.bortolanza.fleet.modules.vehicle.entity.Vehicle;
import com.bortolanza.fleet.modules.vehicle.service.VehicleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final VehicleService vehicleService;
    private final DriverService driverService;
    private final SupplierService supplierService;
    private final MaintenanceMapper maintenanceMapper;

    @Transactional
    public MaintenanceResponseDTO createMaintenance(MaintenanceRequestDTO maintenanceDTO) {
        // 1. Busca e Validação de Entidades via Serviços (Isolamento de Módulo)
        Vehicle vehicle = vehicleService.getAndValidateForMaintenance(maintenanceDTO.getPlate());
        Driver driver = driverService.getById(maintenanceDTO.getDriverId());
        Supplier supplier = supplierService.getById(maintenanceDTO.getSupplierId());

        // 2. Validações de Regra de Negócio Cruzadas
        validateCompanyRelationships(vehicle, driver, supplier);

        // 3. Criação e Mapeamento
        Maintenance maintenance = maintenanceMapper.toEntity(maintenanceDTO);
        maintenance.associateVehicle(vehicle); // Encapsulamento na Entidade
        maintenance.setDriver(driver);
        maintenance.setSupplier(supplier);
        maintenance.open();

        Maintenance savedMaintenance = maintenanceRepository.save(maintenance);

        // 4. Efeito Colateral: Atualização de Quilometragem (Delegado ao Módulo Competente)
        vehicleService.updateMileage(vehicle.getId(), maintenanceDTO.getOdometerAtService());

        return maintenanceMapper.toResponseDTO(savedMaintenance);
    }

    public MaintenanceResponseDTO findById(Long id) {
        return maintenanceRepository.findById(id)
                .map(maintenanceMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Manutenção não encontrada"));
    }

    @Transactional
    public MaintenanceResponseDTO findByInvoiceNumber(String invoiceNumber) {
        String normalizedInvoiceNumber = normalizeInvoiceNumber(invoiceNumber);

        return maintenanceRepository.findByInvoiceNumber(normalizedInvoiceNumber)
                .map(maintenanceMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Manutenção não encontrada"));
    }

    @Transactional
    public List<MaintenanceResponseDTO> findByVehiclePlate(String plate) {
        // Usa o serviço de veículo para normalizar a placa antes da busca
        Vehicle vehicle = vehicleService.getAndValidateForMaintenance(plate);

        return maintenanceRepository.findByVehicle_Plate(vehicle.getPlate())
                .stream()
                .map(maintenanceMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public List<MaintenanceResponseDTO> findAllMaintenances() {
        return maintenanceRepository.findAll().stream()
                .map(maintenanceMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public List<MaintenanceResponseDTO> findByStatus(MaintenanceStatus status) {
        return maintenanceRepository.findByStatus(status)
                .stream()
                .map(maintenanceMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public List<MaintenanceResponseDTO> findByVehicleId(Long vehicleId) {
        return maintenanceRepository.findByVehicleId(vehicleId).stream()
                .map(maintenanceMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public List<MaintenanceResponseDTO> findByCompanyId(Long companyId) {
        return maintenanceRepository.findByCompanyId(companyId).stream()
                .map(maintenanceMapper::toResponseDTO)
                .toList();
    }

    private void validateCompanyRelationships(Vehicle vehicle, Driver driver, Supplier supplier) {
        Long companyId = vehicle.getCompany().getId();

        if (driver != null && !companyId.equals(driver.getCompany().getId())) {
            throw new BusinessException("O motorista não pertence à mesma empresa do veículo");
        }

        if (supplier != null && !companyId.equals(supplier.getCompany().getId())) {
            throw new BusinessException("O fornecedor não pertence à mesma empresa do veículo");
        }
    }

    private String normalizeInvoiceNumber(String invoiceNumber) {
        if (invoiceNumber == null) return null;
        String normalized = invoiceNumber.trim();
        return normalized.isBlank() ? null : normalized;
    }
}
