package com.bortolanza.fleet.modules.supplier.service;

import com.bortolanza.fleet.common.exceptions.ConflictException;
import com.bortolanza.fleet.common.exceptions.ResourceNotFoundException;
import com.bortolanza.fleet.modules.supplier.dto.in.SupplierRequestDTO;
import com.bortolanza.fleet.modules.supplier.dto.out.SupplierResponseDTO;
import com.bortolanza.fleet.modules.supplier.entity.Supplier;
import com.bortolanza.fleet.modules.supplier.mapper.SupplierMapper;
import com.bortolanza.fleet.modules.supplier.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    public SupplierResponseDTO createSupllier(SupplierRequestDTO SupplierDTO) {
        String cnpj = SupplierDTO.getCnpj()
                .replaceAll("\\D", "");
        if(supplierRepository.existsByCnpj(cnpj)) {
            throw new ConflictException("Já existe um fornecedor cadastrado com o CNPJ informado.");
        }

        Supplier entity = supplierMapper.toEntity(SupplierDTO);
        entity.setCnpj(cnpj);

        entity = supplierRepository.save(entity);

        return supplierMapper.toResponseDTO(entity);
    }

    public SupplierResponseDTO findById(Long id){
        return supplierRepository.findById(id)
                .map(supplierMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor nao encontrada."));
    }

    public SupplierResponseDTO findByCnpj(String cnpj){
        cnpj = cnpj.replaceAll("\\D", "");

        Supplier supplier = supplierRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado"));

        return supplierMapper.toResponseDTO(supplier);
    }

    public SupplierResponseDTO findByName(String name) {
        Supplier supplier = supplierRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor nao encontrado"));
        return supplierMapper.toResponseDTO(supplier);


    }

    public List<SupplierResponseDTO> findAll(){
        List<Supplier> suppliers = supplierRepository.findAll();
        return suppliers.stream()
                .map(supplierMapper::toResponseDTO)
                .toList();
    }

    public Supplier getById(Long id) {
        if (id == null) return null;
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado com ID: " + id));
    }

    @Transactional
    public SupplierResponseDTO updateSupplier(Long id, SupplierRequestDTO supplierDTO) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor nao encontrada."));

        String cnpj = supplier.getCnpj().replaceAll("\\D", "");

        if (!supplier.getCnpj().equals(cnpj) && supplierRepository.existsByCnpj(cnpj)) {
            throw new ConflictException("CNPJ já cadastrado.");
        }

        supplierMapper.updateEntity(supplierDTO, supplier);
        supplier.setCnpj(cnpj);

        supplier = supplierRepository.save(supplier);

        return supplierMapper.toResponseDTO(supplier);
    }
}
