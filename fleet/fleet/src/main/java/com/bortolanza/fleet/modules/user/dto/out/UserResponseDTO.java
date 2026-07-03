package com.bortolanza.fleet.modules.user.dto.out;

import com.bortolanza.fleet.modules.user.enums.UserRole;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private UserRole role;
    private Long companyId;
    private boolean active;
    private LocalDateTime createdAt;
}
