package com.gatekeeper.api_management_service.dto.responseDto;

import java.time.LocalDateTime;

import com.gatekeeper.api_management_service.entity.RouteConfigurationVersion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter 
@AllArgsConstructor 
public class RouteVersionResponse {
  private Long id;
  private String routeId;
  private Long versionNumber;
  private String changeType;
  private String configurationSnapshot;
  private LocalDateTime createdAt;

  public static RouteVersionResponse from(RouteConfigurationVersion version) {
      return new RouteVersionResponse(version.getId(), version.getRouteId(), version.getVersionNumber(),
              version.getChangeType(), version.getConfigurationSnapshot(), version.getCreatedAt());
  }
}
