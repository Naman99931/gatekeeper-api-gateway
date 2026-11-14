package com.gatekeeper.api_management_service.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class RouteStatusRequest {
  @NotBlank 
  private boolean active;
}
