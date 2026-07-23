package com.bortolanza.fleet.modules.supplier.entity;

import com.bortolanza.fleet.modules.company.entity.Company;
import com.bortolanza.fleet.modules.supplier.enums.SupplierType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "suppliers",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_supplier_company_cnpj", columnNames = {"company_id", "cnpj"})
        }
)
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

    @Column(length = 20)
    private String phone;

    @Email
    private String email;

    @Column(length = 150)
    private String contactName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private SupplierType type;

    @Column(length = 150)
    private String address;

    @Column(length = 150)
    private String city;

    @Column(length = 2)
    private String state;

    @Column(length = 9)
    private String zipCode;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @Column(length = 1000)
    private String notes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
