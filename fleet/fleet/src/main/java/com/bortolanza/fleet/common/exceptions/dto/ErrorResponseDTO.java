package com.bortolanza.fleet.common.exceptions.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}