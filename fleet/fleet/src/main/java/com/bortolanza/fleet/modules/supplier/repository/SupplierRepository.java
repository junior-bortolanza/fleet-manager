package com.bortolanza.fleet.modules.supplier.repository;

import com.bortolanza.fleet.modules.company.entity.Company;import com.bortolanza.fleet.modules.supplier.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    boolean existsByCnpj(String cnpj);

    Optional<Supplier> findByCnpj(String cnpj);

    Optional<Supplier> findByName(String name);
}
