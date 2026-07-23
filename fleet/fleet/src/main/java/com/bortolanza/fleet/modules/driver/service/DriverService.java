package com.bortolanza.fleet.modules.driver.service;

import com.bortolanza.fleet.common.exceptions.BusinessException;
import com.bortolanza.fleet.common.exceptions.ConflictException;
import com.bortolanza.fleet.common.exceptions.ResourceNotFoundException;
import com.bortolanza.fleet.modules.company.entity.Company;
import com.bortolanza.fleet.modules.company.repository.CompanyRepository;
import com.bortolanza.fleet.modules.driver.dto.in.DriverRequestDTO;
import com.bortolanza.fleet.modules.driver.dto.out.DriverResponseDTO;
import com.bortolanza.fleet.modules.driver.entity.Driver;
import com.bortolanza.fleet.modules.driver.enums.DriverStatus;
import com.bortolanza.fleet.modules.driver.mapper.DriverMapper;
import com.bortolanza.fleet.modules.driver.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;
    private final CompanyRepository companyRepository;
    private final DriverMapper driverMapper;

    @Transactional(readOnly = true)
    public DriverResponseDTO createDriver(DriverRequestDTO dto) {

        String cnh = normalizeCnh(dto.getCnh());

        if(driverRepository.existsByCnh(cnh)){
            throw new ConflictException("CNH já cadastrada");
        }

        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));

        Driver driver = driverMapper.toEntity(dto);
        driver.setCompany(company);
        driver.setCnh(cnh);
        driver.setDriverStatus(DriverStatus.ACTIVE);

        return driverMapper.toResponseDTO(driverRepository.save(driver));

    }

    @Transactional(readOnly = true)
    public DriverResponseDTO findById(Long id) {
        return driverRepository.findById(id)
                .map(driverMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Motorista não encontrado"));
    }

    @Transactional(readOnly = true)
    public DriverResponseDTO findByCnh(String cnh) {
        String normalizeCnh = normalizeCnh(cnh);

        Driver driver = driverRepository.findByCnh(normalizeCnh)
                .orElseThrow(() -> new ResourceNotFoundException("Motorista não encontrado"));
        return driverMapper.toResponseDTO(driver);
    }

    @Transactional(readOnly = true)
    public Page<DriverResponseDTO> findAllPaged(Pageable pageable){

        Page<Driver> drivers = driverRepository.findAll(pageable);

        return drivers.map(driverMapper::toResponseDTO);
    }

    public Driver getById(Long id) {
        if (id == null) return null;
        return driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Motorista não encontrado com ID: " + id));
    }

    @Transactional
    public DriverResponseDTO updateDriver(Long id, DriverRequestDTO dto) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Motorista não encontrado"));

        String cnh = normalizeCnh(dto.getCnh());

        driverMapper.updateEntity(dto, driver);
        driver.setCnh(cnh);

        return driverMapper.toResponseDTO(driverRepository.save(driver));
    }

    private String normalizeCnh(String cnh) {
        if (cnh == null || cnh.isBlank()) {
            throw new BusinessException("CNH é obrigatória");
        }
        return cnh.replaceAll("\\D", "");
    }
}
