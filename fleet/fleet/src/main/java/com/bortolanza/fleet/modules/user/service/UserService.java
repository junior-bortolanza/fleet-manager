package com.bortolanza.fleet.modules.user.service;

import com.bortolanza.fleet.common.exceptions.BusinessException;
import com.bortolanza.fleet.common.exceptions.ConflictException;
import com.bortolanza.fleet.common.exceptions.ResourceNotFoundException;
import com.bortolanza.fleet.modules.user.dto.in.UserRequestDTO;
import com.bortolanza.fleet.modules.user.mapper.UserMapper;
import com.bortolanza.fleet.modules.user.dto.out.UserResponseDTO;
import com.bortolanza.fleet.modules.company.entity.Company;
import com.bortolanza.fleet.modules.user.entity.User;
import com.bortolanza.fleet.modules.company.repository.CompanyRepository;
import com.bortolanza.fleet.modules.user.repository.UserRepository;
import com.bortolanza.fleet.security.JwtUtil;
import lombok.RequiredArgsConstructor;


import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserResponseDTO createUser(UserRequestDTO dto) {
        emailExists(dto.getEmail());
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company with id " + dto.getCompanyId() + " not found"));

        User user = userMapper.toEntity(dto);
        user.setCompany(company);
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));

        return userMapper.toResponseDTO(userRepository.save(user));
    }

    public void emailExists(String email) {
        try {
            boolean exists = userRepository.existsByEmail(email);
            if(exists) {
                throw new UsernameNotFoundException("Email already exists");
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email already exists" + e.getCause());
        }
    }

    public void deleteUser(String email) {
        userRepository.deleteByEmail(email);
    }

    public UserResponseDTO updateUserData(String token, UserRequestDTO dto) {
        //Aqui buscamos o email do usuario atraves do token (tirar a obrigatoriedade do email)
        String email = jwtUtil.extractEmailToken(token.substring(7));

        //Criptografia de senha
        dto.setPassword(dto.getPassword() != null ? passwordEncoder.encode(dto.getPassword()) : null);

        //Busca os dados do usuário no banco de dados
        User userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Email not found"));

        //Mesclou os dados que recebemos na requisiÇão DTO com os dados do banco de dados
        userMapper.updateEntity(dto, userEntity);

        // Salvou os dados do usuário convertido e depois pegou o retorno e converteu para UsuarioDTO
        return userMapper.toResponseDTO(userRepository.save(userEntity));

    }
}
