package com.gatekeeper.api_management_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gatekeeper.api_management_service.dto.requestDto.OrganizationRequest;
import com.gatekeeper.api_management_service.dto.requestDto.TenantAdminRequest;
import com.gatekeeper.api_management_service.dto.responseDto.TenantAdminResponse;
import com.gatekeeper.api_management_service.dto.responseDto.OrganizationResponse;
import com.gatekeeper.api_management_service.service.OrganizationService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController 
@RequestMapping("/api/platform/organizations")
public class OrganizationController {
    
    private final OrganizationService organizationService;
    
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }
    
    @PostMapping
    public ResponseEntity<OrganizationResponse> createOrganization(@Valid @RequestBody OrganizationRequest organizationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(organizationService.createOrganization(organizationRequest));
    }
    
    @GetMapping
    public ResponseEntity<List<OrganizationResponse>> getAllOrganizations() {
        return ResponseEntity.ok().body(organizationService.getAllOrganizations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationResponse> getOrganizationById(@PathVariable int id) {
        OrganizationResponse organization = organizationService.getOrganizationById(id);
        if (organization == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(organization);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrganizationResponse> updateOrganization(@PathVariable int id, @Valid @RequestBody OrganizationRequest organizationRequest) {
        return ResponseEntity.ok(organizationService.updateOrganization(id, organizationRequest));
    }

    @PatchMapping ("/{id}/status")
    public ResponseEntity<OrganizationResponse> updateOrganizationStatus(@PathVariable int id, @Valid @RequestBody OrganizationRequest organizationRequest) {
        return ResponseEntity.ok(organizationService.updateOrganizationStatus(id, organizationRequest));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable int id) {
        organizationService.deleteOrganization(id);
        return ResponseEntity.noContent().build();
    }

    // Tenant creation endpoint
    @PostMapping("/createTenant")
    public ResponseEntity<TenantAdminResponse> CreateTenantAdmin(@RequestBody TenantAdminRequest request, @Valid @RequestParam int organizationId) {
        TenantAdminResponse response = organizationService.createTenantAdmin(request, organizationId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getTenant/{id}")
    public ResponseEntity<TenantAdminResponse> getTenantAdmin(@PathVariable int id) {
        TenantAdminResponse response = organizationService.getTenantAdmin(id);
        return ResponseEntity.ok().body(response);
    }
    
    @PutMapping("/updateTenant/{id}")
    public ResponseEntity<TenantAdminResponse> updateTenantAdmin(@PathVariable int id, @Valid @RequestBody TenantAdminRequest tenantAdminRequest) {
        TenantAdminResponse response = organizationService.updateTenantAdmin(id, tenantAdminRequest);
        return ResponseEntity.ok().body(response);
    }
}