package com.gatekeeper.api_management_service.dto.responseDto;

import java.time.LocalDateTime;

import com.gatekeeper.api_management_service.entity.Organization;
import com.gatekeeper.api_management_service.entity.OrganizationStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter 
@AllArgsConstructor 
public class OrganizationResponse {
  private Integer id;
  private String name;
  private String slug;
  private OrganizationStatus status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static OrganizationResponse fromEntity(Organization organization) {
    return new OrganizationResponse(organization.getId(),
                organization.getName(),
                organization.getSlug(),
                organization.getStatus(),
                organization.getCreatedAt(),
                organization.getUpdatedAt());
  }
}
