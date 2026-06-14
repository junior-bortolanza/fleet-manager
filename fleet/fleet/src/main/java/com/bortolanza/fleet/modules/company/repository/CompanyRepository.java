package com.bortolanza.fleet.modules.repository;

import com.bortolanza.fleet.modules.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {

    Company findByEmail(String email);

    boolean existsByCnpj(String cnpj);
}
