package com.gatekeeper.smart_gateway_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
@AllArgsConstructor 
public class LoginResponse {
  private String accessToken;
    private String tokenType;
    private long expiresIn;
    private String role;
    private Long organizationId;
}
