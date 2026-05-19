package com.bortolanza.fleet.infrastructure.repositories;

import com.bortolanza.fleet.infrastructure.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
}
