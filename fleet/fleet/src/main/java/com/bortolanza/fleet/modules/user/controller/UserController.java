package com.bortolanza.fleet.modules.user.controller;

import com.bortolanza.fleet.modules.user.dto.in.UserRequestDTO;
import com.bortolanza.fleet.modules.user.dto.out.UserResponseDTO;
import com.bortolanza.fleet.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userDTO) {
        UserResponseDTO newDto = userService.createUser(userDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @DeleteMapping(value ="/{id}" )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUserByEmail(@PathVariable String email) {
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserRequestDTO userDTO,
                                                      @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.updateUserData(token, userDTO));
    }
}
