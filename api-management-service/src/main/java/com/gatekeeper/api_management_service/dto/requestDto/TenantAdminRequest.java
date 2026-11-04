package com.gatekeeper.api_management_service.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter 
public class TenantAdminRequest {
  @NotBlank 
  @Size (min = 3, max = 50)
  private String username;

  @NotBlank
  @Size (min = 8, max = 100)
  private String passwordHash;

  private boolean enabled = true;
}
