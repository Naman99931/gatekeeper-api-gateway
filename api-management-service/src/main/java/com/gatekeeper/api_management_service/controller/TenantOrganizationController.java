package com.gatekeeper.api_management_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gatekeeper.api_management_service.dto.requestDto.TenantOrganizationRequest;
import com.gatekeeper.api_management_service.dto.responseDto.OrganizationResponse;
import com.gatekeeper.api_management_service.service.OrganizationService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController 
@RequestMapping ("/api/tenant/organizations")
public class TenantOrganizationController {

  private final OrganizationService organizationService;
  
  public TenantOrganizationController(OrganizationService organizationService) {
    this.organizationService = organizationService;
  }

  @GetMapping
  public ResponseEntity<OrganizationResponse> getOwnOrganization() {
      return ResponseEntity.ok(organizationService.getOwnOrganization());
  }

  @PutMapping
  public ResponseEntity<OrganizationResponse> updateOwnOrganization(@Valid @RequestBody TenantOrganizationRequest request) {
      return ResponseEntity.ok(organizationService.updateOrganization(request));
  }
}
