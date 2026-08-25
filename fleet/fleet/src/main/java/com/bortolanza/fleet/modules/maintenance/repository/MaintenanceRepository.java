package com.bortolanza.fleet.modules.maintenance.repository;

import com.bortolanza.fleet.modules.maintenance.entity.Maintenance;
import com.bortolanza.fleet.modules.maintenance.enums.MaintenanceStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    @EntityGraph(attributePaths = {"vehicle", "driver", "supplier"})
    List<Maintenance> findByVehicleId(Long vehicleId);

    @EntityGraph(attributePaths = {"vehicle", "driver", "supplier"})
    List<Maintenance> findByCompanyId(Long companyId);

    @EntityGraph(attributePaths = {"vehicle", "driver", "supplier"})
    List<Maintenance> findByStatus(MaintenanceStatus status);

    @EntityGraph(attributePaths = {"vehicle", "driver", "supplier"})
    List<Maintenance> findAll();

    Optional<Maintenance> findByInvoiceNumber(String invoiceNumber);

    Optional<Maintenance> findByVehicle_Plate(String plate);
}
