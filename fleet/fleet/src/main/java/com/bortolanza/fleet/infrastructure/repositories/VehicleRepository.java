package com.bortolanza.fleet.infrastructure.repositories;

import com.bortolanza.fleet.infrastructure.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
}
