package com.bortolanza.fleet.modules.driver.repository;


import com.bortolanza.fleet.modules.driver.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface DriverRepository extends JpaRepository<Driver, Long> {

    boolean existsByCnh(String cnh);

    Optional<Driver> findByCnh(String cnh);
}
