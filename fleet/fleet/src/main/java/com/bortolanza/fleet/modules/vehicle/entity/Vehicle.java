package com.bortolanza.fleet.modules.entity;

import com.bortolanza.fleet.modules.company.entity.Company;
import com.bortolanza.fleet.modules.enums.VehicleStatus;
import com.bortolanza.fleet.modules.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "vehicles",
        indexes = {
                @Index(name = "idx_vehicle_company", columnList = "company_id"),
                @Index(name = "idx_vehicle_status", columnList = "status"),
                @Index(name = "idx_vehicle_active", columnList = "active")
        }
)
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false, unique = true, length = 8)
    private String plate;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    private String color;

    @Column(nullable = false)
    private Integer manufacturingYear;

    @Column(nullable = false)
    private Integer modelYear;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal loadCapacityKg;

    @Column(nullable = false, unique = true,  length = 11)
    private String renavam;

    @Column(nullable = false, unique = true,  length = 17)
    private String chassis;

    private LocalDate acquisitionDate;
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType vehicleType;

    @Column(nullable = false)
    private Long currentMileage = 0L;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleStatus status = VehicleStatus.ACTIVE;

    @Column(nullable = false)
    private boolean active = true;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
