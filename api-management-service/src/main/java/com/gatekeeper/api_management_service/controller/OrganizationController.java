package com.gatekeeper.api_management_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gatekeeper.api_management_service.dto.requestDto.OrganizationRequest;
import com.gatekeeper.api_management_service.dto.responseDto.OrganizationResponse;
import com.gatekeeper.api_management_service.service.OrganizationService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @PatchMapping("/activateOrganization/{id}")
    public ResponseEntity<OrganizationResponse> updateOrganizationActiveStatus(@PathVariable int id) {
        OrganizationResponse organization = organizationService.updateOrganizationActiveStatus(id);
        if (organization == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(organization);
    }
}