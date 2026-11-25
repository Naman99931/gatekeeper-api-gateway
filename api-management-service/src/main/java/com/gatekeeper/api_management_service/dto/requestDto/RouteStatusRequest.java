package com.gatekeeper.api_management_service.dto.requestDto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class RouteStatusRequest {
  @NotNull(message = "Active status is required")
  private boolean active;
}
