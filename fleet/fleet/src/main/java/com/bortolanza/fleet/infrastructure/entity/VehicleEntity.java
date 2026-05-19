package com.bortolanza.fleet.infrastructure.entity;

import com.bortolanza.fleet.infrastructure.enums.VehicleStatus;
import com.bortolanza.fleet.infrastructure.enums.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicles")
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;

    @Column(nullable = false, unique = true, length = 8)
    private String plate;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private String model;
    private String color;
    @Column(nullable = false)
    private Integer year;
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
