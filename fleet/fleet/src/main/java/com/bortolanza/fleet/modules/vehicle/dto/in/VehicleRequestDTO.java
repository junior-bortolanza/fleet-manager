package com.bortolanza.fleet.modules.dto.in;

import com.bortolanza.fleet.modules.enums.VehicleType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank
    private String renavam;

    @NotBlank
    private String chassis;

    private LocalDate acquisitionDate;
    private String imageUrl;

    @NotNull
    private VehicleType vehicleType;

}
