package com.bortolanza.fleet.modules.dto.out;

import com.bortolanza.fleet.modules.enums.VehicleStatus;
import com.bortolanza.fleet.modules.enums.VehicleType;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleResponseDTO {

    private UUID id;
    private UUID companyId;
    private String plate;
    private String brand;
    private String model;
    private String color;
    private Integer manufacturingYear;
    private Integer modelYear;
    private BigDecimal loadCapacityKg;
    private String renavam;
    private String chassis;
    private LocalDate acquisitionDate;
    private boolean active;
    private String imageUrl;
    private VehicleType vehicleType;
    private Long currentMileage;
    private VehicleStatus status;

}
