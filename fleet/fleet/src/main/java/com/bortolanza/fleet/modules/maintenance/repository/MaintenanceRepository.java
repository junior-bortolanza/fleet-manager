package com.bortolanza.fleet.modules.maintenance.repository;


import com.bortolanza.fleet.modules.maintenance.entity.Maintenance;
import com.bortolanza.fleet.modules.maintenance.enums.MaintenanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    List<Maintenance> findByVehicleId(Long vehicleId);
    List<Maintenance> findByCompanyId(Long companyId);
    List<Maintenance> findByStatus(MaintenanceStatus status);

    Optional<Maintenance> findByInvoiceNumber(String invoiceNumber);

    Optional<Maintenance> findByVehicle_Plate(String plate);
}
