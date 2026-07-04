package com.bortolanza.fleet.modules.vehicle.repository;

import com.bortolanza.fleet.modules.vehicle.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    boolean existsByPlate(String plate);

    Optional<Vehicle> findByPlate(String plate);

}
