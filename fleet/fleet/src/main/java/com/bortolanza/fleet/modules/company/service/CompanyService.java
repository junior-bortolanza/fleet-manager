package com.bortolanza.fleet.modules.company.service;

import com.bortolanza.fleet.common.exceptions.ConflictException;
import com.bortolanza.fleet.common.exceptions.ResourceNotFoundException;
import com.bortolanza.fleet.modules.company.dto.in.CompanyRequestDTO;
import com.bortolanza.fleet.modules.company.dto.out.CompanyResponseDTO;
import com.bortolanza.fleet.modules.company.entity.Company;
import com.bortolanza.fleet.modules.company.mapper.CompanyMapper;
import com.bortolanza.fleet.modules.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyResponseDTO createCompany(CompanyRequestDTO companyDTO) {
        String cnpj = companyDTO.getCnpj()
                .replaceAll("\\D", "");
        if(companyRepository.existsByCnpj(cnpj)){
            throw new ConflictException("Já existe uma empresa cadastrada com o CNPJ informado.");
        }
        Company entity = companyMapper.toEntity(companyDTO);
        entity.setCnpj(cnpj);

        entity = companyRepository.save(entity);

        return companyMapper.toResponseDTO(entity);
    }

    public CompanyResponseDTO findById(UUID id) {
        return companyRepository.findById(id)
                .map(companyMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa nao encontrada."));
    }

    public CompanyResponseDTO findByCnpj(String cnpj) {
        cnpj = cnpj.replaceAll("\\D", "");

        Company company = companyRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa nao encontrada."));

        return companyMapper.toResponseDTO(company);
    }

    public CompanyResponseDTO findByName(String name) {
       Company company = companyRepository.findByName(name)
               .orElseThrow(() -> new ResourceNotFoundException("Empresa nao encontrada."));

       return companyMapper.toResponseDTO(company);
    }

    public List<CompanyResponseDTO> findAll() {
        List<Company> companies = companyRepository.findAll();

        return companies.stream()
                .map(companyMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public CompanyResponseDTO updateCompany(UUID id, CompanyRequestDTO companyDTO) {
       Company company = companyRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Empresa nao encontrada."));

        String cnpj = companyDTO.getCnpj().replaceAll("\\D", "");

        if (!company.getCnpj().equals(cnpj) && companyRepository.existsByCnpj(cnpj)) {
            throw new ConflictException("CNPJ já cadastrado.");
        }

       companyMapper.updateEntity(companyDTO, company);
       company.setCnpj(cnpj);

       company = companyRepository.save(company);

       return companyMapper.toResponseDTO(company);
    }
}
