package com.gatekeeper.api_management_service.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter 
@AllArgsConstructor 
public class LoginResponseDto {
    private String accessToken;
    private String tokenType;
    private long expiresIn;
    private String role;
    private Integer organizationId;
}
