package com.gatekeeper.api_management_service.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.gatekeeper.api_management_service.entity.Organization;
import com.gatekeeper.api_management_service.entity.OrganizationStatus;
import com.gatekeeper.api_management_service.exception.ApiException;
import com.gatekeeper.api_management_service.repository.OrganizationRepository;

@Service 
public class TenantContext {
  private final OrganizationRepository organizationRepository;

  public TenantContext(OrganizationRepository organizationRepository) {
    this.organizationRepository = organizationRepository;
  }

  public Organization getOrganization() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if(!(authentication instanceof JwtAuthenticationToken)) {
      throw new ApiException(HttpStatus.FORBIDDEN, "Tenant identity is missing"); 
    }

    Long organizationId = ((JwtAuthenticationToken) authentication).getToken().getClaim("organizationId");  

    if(organizationId == null) {
      throw new ApiException(HttpStatus.FORBIDDEN, "Tenant identity is missing"); 
    }

    Organization organization = organizationRepository.findById(organizationId.intValue()).orElse(null);
    if(organization == null) {
      throw new ApiException(HttpStatus.FORBIDDEN, "Invalid tenant identity"); 
    }

    if(organization.getStatus() != null && organization.getStatus() != OrganizationStatus.ACTIVE) {
      throw new ApiException(HttpStatus.FORBIDDEN, "Tenant is not active"); 
    }
    return organization;
  }
}
