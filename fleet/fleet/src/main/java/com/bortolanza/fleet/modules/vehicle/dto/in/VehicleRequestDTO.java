package com.bortolanza.fleet.modules.vehicle.dto.in;

import com.bortolanza.fleet.modules.vehicle.enums.VehicleStatus;
import com.bortolanza.fleet.modules.vehicle.enums.VehicleType;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleRequestDTO {

    @NotNull
    private UUID companyId;

    @NotBlank
    @Size(min = 7, max = 8)
    @Column(unique = true)
    private String plate;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;


    private String color;

    @NotNull
    private Integer manufacturingYear;

    @NotNull
    private Integer modelYear;

    @NotNull
    private BigDecimal loadCapacityKg;

    @NotNull
    private Long currentMileage;

    @NotBlank
    private String renavam;

    @NotBlank
    private String chassis;

    private LocalDate acquisitionDate;
    private String imageUrl;

    @NotNull
    private VehicleType vehicleType;

    @NotNull
    private VehicleStatus vehicleStatus;

}
