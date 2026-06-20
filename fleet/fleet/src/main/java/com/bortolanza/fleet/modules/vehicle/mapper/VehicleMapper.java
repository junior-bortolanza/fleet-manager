package com.bortolanza.fleet.modules.vehicle.mapper;

import com.bortolanza.fleet.modules.vehicle.entity.Vehicle;
import com.bortolanza.fleet.modules.vehicle.dto.in.VehicleRequestDTO;
import com.bortolanza.fleet.modules.vehicle.dto.out.VehicleResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(source = "vehicleStatus", target = "status")
    @Mapping(source = "companyId", target = "company.id")
    Vehicle toEntity(VehicleRequestDTO dto);

    @Mapping(source = "company.id", target = "companyId")
    VehicleResponseDTO toResponseDTO(Vehicle entity);

    @Mapping(source = "vehicleStatus", target = "status")
    @Mapping(source = "companyId", target = "company.id")
    void updateEntity(VehicleRequestDTO dto, @MappingTarget Vehicle vehicle);
}
