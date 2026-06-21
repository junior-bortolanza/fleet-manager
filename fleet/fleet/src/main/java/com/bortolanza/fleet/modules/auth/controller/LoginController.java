package com.bortolanza.fleet.modules.auth.controller;
import org.springframework.web.bind.annotation.PostMapping;
import com.bortolanza.fleet.modules.auth.dto.LoginRequestDTO;
import com.bortolanza.fleet.modules.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginDto) {
        String token = authService.authenticate(loginDto);
        return ResponseEntity.ok(token);
    }
}
