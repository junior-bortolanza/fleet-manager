package com.bortolanza.fleet.modules.company.repository;
import com.bortolanza.fleet.modules.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {


    boolean existsByCnpj(String cnpj);

    Optional<Company> findByCnpj(String cnpj);

    Optional<Company> findByName(String name);
}
