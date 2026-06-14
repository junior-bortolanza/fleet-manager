package com.bortolanza.fleet.modules.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "companies")
public class Company {
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

        @Column(length = 100)
        private String city;

        @Column(length = 2)
        private String state;

        @Column(length = 9)
        private String zipCode;

        @Column(nullable = false)
        private boolean active = true;

        @CreationTimestamp
        @Column(nullable = false, updatable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        private LocalDateTime updatedAt;
}
