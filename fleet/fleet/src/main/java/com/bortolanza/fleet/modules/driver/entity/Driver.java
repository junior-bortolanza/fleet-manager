package com.bortolanza.fleet.modules.driver.entity;

import com.bortolanza.fleet.modules.company.entity.Company;
import com.bortolanza.fleet.modules.driver.enums.CnhCategory;

import com.bortolanza.fleet.modules.driver.enums.DriverStatus;
import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String driveName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false, unique = true, length = 20)
    private String cnh;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CnhCategory cnhCategory;

    @Column(nullable = false)
    private LocalDate cnhExpiration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DriverStatus driverStatus;


    private String moppNumber;

    private LocalDate moppExpirationDate;

    @Column(nullable = false)
    private LocalDate birthDate;

    private String phone;
    private String email;

    @Column(length = 1000)
    private String notes;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
