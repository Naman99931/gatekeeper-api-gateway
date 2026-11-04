package com.gatekeeper.api_management_service.dto.responseDto;

import java.time.LocalDateTime;

import com.gatekeeper.api_management_service.entity.ManagementUser;
import com.gatekeeper.api_management_service.entity.Organization;
import com.gatekeeper.api_management_service.entity.Role;

import lombok.Getter;

@Getter
public class TenantAdminResponse {
  
  private int id;
  private Organization organization;
  private Role role;
  private String username;
  private boolean enabled;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static TenantAdminResponse fromEntity(ManagementUser managementUser) {
    TenantAdminResponse response = new TenantAdminResponse();
    response.id = managementUser.getId();
    response.organization = managementUser.getOrganization();
    response.role = managementUser.getRole();
    response.username = managementUser.getUsername();
    response.enabled = managementUser.isEnabled();
    response.createdAt = managementUser.getCreatedAt();
    response.updatedAt = managementUser.getUpdatedAt();
    return response;
  }
}
