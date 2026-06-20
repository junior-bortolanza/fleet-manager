package com.bortolanza.fleet.modules.driver.entity;

import com.bortolanza.fleet.modules.company.entity.Company;
import com.bortolanza.fleet.modules.driver.enums.CnhCategory;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

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

    @Column(nullable = false)
    private LocalDate birthDate;

    private String phone;

    private String email;
}
