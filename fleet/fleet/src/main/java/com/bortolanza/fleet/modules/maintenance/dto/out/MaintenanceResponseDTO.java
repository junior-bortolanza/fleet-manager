package com.bortolanza.fleet.modules.maintenance.dto.out;

import com.bortolanza.fleet.modules.maintenance.enums.MaintenanceCategory;
import com.bortolanza.fleet.modules.maintenance.enums.MaintenanceLocation;
import com.bortolanza.fleet.modules.maintenance.enums.MaintenanceStatus;
import com.bortolanza.fleet.modules.maintenance.enums.MaintenanceType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaintenanceResponseDTO {
    private Long id;

    private Long vehicleId;
    private String vehiclePlate;
    private String vehicleModel;

    private Long driverId;
    private String driverName;

    private Long supplierId;
    private String supplierName;

    private Long openedById;
    private String openedByName;

    private Long closedById;
    private String closedByName;

    private MaintenanceStatus status;
    private MaintenanceType maintenanceType;
    private MaintenanceCategory maintenanceCategory;
    private MaintenanceLocation maintenanceLocation;

    private LocalDate startDate;
    private LocalDate endDate;

    private Long odometerAtService;
    private BigDecimal totalCost;

    private String invoiceNumber;
    private String notes;
    private String photoUrl;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
