package com.bortolanza.fleet.modules.company.mapper;

import com.bortolanza.fleet.modules.company.dto.in.CompanyRequestDTO;
import com.bortolanza.fleet.modules.company.dto.out.CompanyResponseDTO;
import com.bortolanza.fleet.modules.company.entity.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    Company toEntity(CompanyRequestDTO dto);

    CompanyResponseDTO toResponseDTO(Company entity);
}
