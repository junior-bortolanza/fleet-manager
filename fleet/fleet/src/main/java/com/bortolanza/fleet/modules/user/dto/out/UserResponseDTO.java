package com.bortolanza.fleet.modules.user.dto.out;

import com.bortolanza.fleet.modules.enums.UserRole;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    private UUID id;
    private String name;
    private String email;
    private UserRole role;
    private UUID companyId;
    private boolean active;
    private LocalDateTime createdAt;
}
