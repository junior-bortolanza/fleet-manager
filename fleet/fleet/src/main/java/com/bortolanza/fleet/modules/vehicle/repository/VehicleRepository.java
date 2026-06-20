package com.bortolanza.fleet.modules.vehicle.repository;

import com.bortolanza.fleet.modules.vehicle.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.net.http.HttpHeaders;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {

    boolean existsByPlate(String plate);

    Optional<Vehicle> findByPlate(String plate);
}
