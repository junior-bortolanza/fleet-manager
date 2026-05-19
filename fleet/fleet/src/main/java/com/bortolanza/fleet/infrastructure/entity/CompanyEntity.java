package com.bortolanza.fleet.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "companies")
public class CompanyEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;
        @Column(nullable = false)
        private String name;
        @Column(nullable = false, unique = true, length = 18)
        private String cnpj;

        private String email;
        private String phone;
        private String address;
        private String city;
        private String state;
        private String zipCode;

        @Column(nullable = false)
        private boolean active = true;

        @CreationTimestamp
        @Column(nullable = false, updatable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        private LocalDateTime updatedAt;
}
