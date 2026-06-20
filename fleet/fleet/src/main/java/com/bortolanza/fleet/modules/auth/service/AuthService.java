package com.bortolanza.fleet.modules.auth.service;

import com.bortolanza.fleet.common.exceptions.UnauthorizedException;
import com.bortolanza.fleet.modules.auth.dto.LoginRequestDTO;
import com.bortolanza.fleet.security.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public String authenticate(LoginRequestDTO dto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
            );
            return jwtUtil.generateToken(authentication.getName());
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            throw new UnauthorizedException("Email ou senha inválidos");
        }
    }
}