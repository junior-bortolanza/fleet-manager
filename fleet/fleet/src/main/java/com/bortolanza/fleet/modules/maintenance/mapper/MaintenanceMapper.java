package com.bortolanza.fleet.modules.maintenance.mapper;

import com.bortolanza.fleet.modules.maintenance.dto.in.MaintenanceRequestDTO;
import com.bortolanza.fleet.modules.maintenance.dto.out.MaintenanceResponseDTO;
import com.bortolanza.fleet.modules.maintenance.entity.Maintenance;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface MaintenanceMapper{

    Maintenance toEntity(MaintenanceRequestDTO dto);

    MaintenanceResponseDTO toResponseDTO(Maintenance entity);

    void updateEntity(MaintenanceRequestDTO dto, @MappingTarget Maintenance entity);


}
