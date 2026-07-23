package com.bortolanza.fleet.modules.driver.mapper;

import com.bortolanza.fleet.modules.driver.dto.in.DriverRequestDTO;
import com.bortolanza.fleet.modules.driver.dto.out.DriverResponseDTO;
import com.bortolanza.fleet.modules.driver.entity.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DriverMapper {

    Driver toEntity(DriverRequestDTO dto);

    DriverResponseDTO toResponseDTO(Driver driver);

    void updateEntity(DriverRequestDTO dto, @MappingTarget Driver entity);
}
