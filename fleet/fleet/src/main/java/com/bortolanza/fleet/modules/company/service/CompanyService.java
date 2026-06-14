package com.bortolanza.fleet.modules.service;

import com.bortolanza.fleet.modules.company.dto.in.CompanyRequestDTO;
import com.bortolanza.fleet.modules.company.dto.out.CompanyResponseDTO;
import com.bortolanza.fleet.modules.entity.Company;
import com.bortolanza.fleet.modules.dto.mapper.CompanyMapper;
import com.bortolanza.fleet.modules.exceptions.BusinessException;
import com.bortolanza.fleet.modules.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyResponseDTO createCompany(CompanyRequestDTO companyDTO) {
        if(companyRepository.existsByCnpj(companyDTO.getCnpj())) {
            throw new BusinessException("Company already exists");
        }
        Company entity = companyMapper.toEntity(companyDTO);
        entity = companyRepository.save(entity);
        return companyMapper.toResponseDTO(entity);
    }
}
