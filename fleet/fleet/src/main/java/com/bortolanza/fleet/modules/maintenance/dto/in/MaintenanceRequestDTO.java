package com.bortolanza.fleet.modules.maintenance.dto.in;

import com.bortolanza.fleet.modules.maintenance.enums.MaintenanceCategory;

import com.bortolanza.fleet.modules.maintenance.enums.MaintenanceType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaintenanceRequestDTO {

    @NotNull(message = "Placa do veiculo obrigatória")
    private String plate;

    private Long driverId;

    private Long supplierId;

    @NotNull(message = "Tipo da manutenção obrigatório")
    private MaintenanceType maintenanceType;

    @NotNull(message = "Categoria obrigatório")
    private MaintenanceCategory maintenanceCategory;

    @NotNull(message = "Odômetro obrigatório")
    private Long odometerAtService;

    private LocalDate startDate;
    private LocalDate endDate;

    @Size(max = 50, message = "Número da nota fiscal deve ter no máximo 50 caracteres")
    private String invoiceNumber;

    @Size(max = 1000, message = "Número da nota fiscal deve ter no máximo 1000 caracteres")
    private String notes;

    private String photoUrl;
}
