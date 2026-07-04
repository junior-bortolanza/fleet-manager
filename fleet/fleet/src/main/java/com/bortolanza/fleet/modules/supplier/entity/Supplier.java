package com.bortolanza.fleet.modules.supplier;

import com.bortolanza.fleet.modules.company.entity.Company;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(length = 120)
    private String name;

    @Column(length = 14)
    private String cnpj;

    @Column(length = 11)
    private String phone;

    @Column(length = 14)
    @Email
    private String email;

    @Column(length = 150)
    private String contactName;

    @Column(length = 150)
    private String address;

    @Column(length = 150)
    private String city;

    @Column(length = 2)
    private String state;

    @Column(length = 9)
    private String zipCode;

    @Column(nullable = false)
    private Boolean active = true;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
