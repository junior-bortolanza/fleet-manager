package com.bortolanza.fleet.modules.supplier.mapper;
import com.bortolanza.fleet.modules.supplier.dto.in.SupplierRequestDTO;
import com.bortolanza.fleet.modules.supplier.dto.out.SupplierResponseDTO;
import com.bortolanza.fleet.modules.supplier.entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    Supplier toEntity(SupplierRequestDTO dto);

    SupplierResponseDTO toResponseDTO(Supplier entity);

    void updateEntity(SupplierRequestDTO dto, @MappingTarget Supplier entity);
}
