package com.gatekeeper.api_management_service.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.common.errors.ApiException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gatekeeper.api_management_service.dto.requestDto.TenantAdminRequest;
import com.gatekeeper.api_management_service.dto.requestDto.OrganizationRequest;
import com.gatekeeper.api_management_service.dto.responseDto.TenantAdminResponse;
import com.gatekeeper.api_management_service.dto.responseDto.OrganizationResponse;
import com.gatekeeper.api_management_service.entity.ManagementUser;
import com.gatekeeper.api_management_service.entity.Organization;
import com.gatekeeper.api_management_service.entity.OrganizationStatus;
import com.gatekeeper.api_management_service.entity.Role;
import com.gatekeeper.api_management_service.repository.ManagementUserRepository;
import com.gatekeeper.api_management_service.repository.OrganizationRepository;
import com.gatekeeper.api_management_service.repository.RoleRepository;
import org.springframework.transaction.annotation.Transactional;

@Service 
public class OrganizationService {

    private OrganizationRepository organizationRepository;
    private ManagementUserRepository managementUserRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public OrganizationService(OrganizationRepository organizationRepository,
                               ManagementUserRepository managementUserRepository,
                               RoleRepository roleRepository,
                               PasswordEncoder passwordEncoder) {
                                 this.organizationRepository = organizationRepository;
                                 this.managementUserRepository = managementUserRepository;
                                 this.roleRepository = roleRepository;
                                 this.passwordEncoder = passwordEncoder;
                               }

    @Transactional
    public OrganizationResponse createOrganization(OrganizationRequest organizationRequest) {
        checkUnique(organizationRequest.getName(), organizationRequest.getSlug(), null);

        Organization organization = new Organization();
        organization.setName(organizationRequest.getName());
        organization.setSlug(organizationRequest.getSlug());
        organization.setStatus(OrganizationStatus.PENDING);
        organization.setCreatedAt(LocalDateTime.now());
        organization.setUpdatedAt(LocalDateTime.now());

        organizationRepository.save(organization);

        return OrganizationResponse.fromEntity(organization);
    }

    @Transactional(readOnly = true)
    public List<OrganizationResponse> getAllOrganizations() {
        List<OrganizationResponse> response = new ArrayList<OrganizationResponse>();

        for (Organization organization : organizationRepository.findAllByStatusNot(OrganizationStatus.DELETED)) {
            response.add(OrganizationResponse.fromEntity(organization));
        }
        return response;
    }

    @Transactional (readOnly = true)
    public OrganizationResponse getOrganizationById(int id) {
        Organization organization = organizationRepository.findById(id);
        return OrganizationResponse.fromEntity(organization);
    }

    @Transactional
    public OrganizationResponse updateOrganization(int id, OrganizationRequest organizationRequest) {
        Organization organization = getOrganization(id);

        checkUnique(organizationRequest.getName(), organizationRequest.getSlug(), id);

        organization.setName(organizationRequest.getName());
        organization.setSlug(organizationRequest.getSlug());
        organization.setUpdatedAt(LocalDateTime.now());
        organizationRepository.save(organization);
        return OrganizationResponse.fromEntity(organization);
    }

    @Transactional
    public OrganizationResponse updateOrganizationStatus(int id, OrganizationRequest organizationRequest) {
        Organization organization = organizationRepository.findById(id);
        if (organization == null) {
            return null;
        }

        if(organization.getStatus() == OrganizationStatus.DELETED) {
            return null;
        }

        organization.setStatus(organization.getStatus());
        organization.setUpdatedAt(LocalDateTime.now());
        organizationRepository.save(organization);
        return OrganizationResponse.fromEntity(organization);
    }

    @Transactional
    public OrganizationResponse deleteOrganization(int id) {
        Organization organization = organizationRepository.findById(id);
        if (organization == null) {
            return null;
        }
        organization.setStatus(OrganizationStatus.DELETED);
        organization.setUpdatedAt(LocalDateTime.now());
        organizationRepository.save(organization);
        return OrganizationResponse.fromEntity(organization);
    }

    // TenantAdmin Endpoints
    
    @Transactional 
    public TenantAdminResponse createTenantAdmin(TenantAdminRequest request, int organizationId) {
        Organization organization = organizationRepository.findById(organizationId);
        
        if(managementUserRepository.findByOrganizationId(organizationId).isPresent()) {
            throw new ApiException("This organization already has a tenant administrator");
        }
        
        if(managementUserRepository.existsByUsername(request.getUsername())) {
            throw new ApiException("Username already exists");
        }

        Role role = roleRepository.findByName("TENANT_ADMIN")
                .orElseThrow(() -> new ApiException("TENANT_ADMIN role is missing"));

        ManagementUser tenantAdmin = new ManagementUser();
        tenantAdmin.setOrganization(organization);
        tenantAdmin.setRole(role);
        tenantAdmin.setUsername(request.getUsername());
        tenantAdmin.setPasswordHash(passwordEncoder.encode(request.getPasswordHash()));
        tenantAdmin.setEnabled(request.isEnabled());
        tenantAdmin.setCreatedAt(LocalDateTime.now());
        tenantAdmin.setUpdatedAt(LocalDateTime.now());

        managementUserRepository.save(tenantAdmin);

        return TenantAdminResponse.fromEntity(tenantAdmin);
    }

    @Transactional(readOnly = true)
    public TenantAdminResponse getTenantAdmin(int tenantAdminId) {
        ManagementUser tenantAdmin = managementUserRepository.findById(tenantAdminId)
                .orElseThrow(() -> new ApiException("Tenant administrator not found for the given ID"));
        return TenantAdminResponse.fromEntity(tenantAdmin);
    }

    @Transactional
    public TenantAdminResponse updateTenantAdmin(int organizationId, TenantAdminRequest request) {
        ManagementUser tenantAdmin = getTenantAdminEntity(organizationId);

        if(!tenantAdmin.getUsername().equals(request.getUsername())
                && managementUserRepository.existsByUsername(request.getUsername())) {
            throw new ApiException("Username already exists");
        }   

        tenantAdmin.setUsername(request.getUsername());
        tenantAdmin.setPasswordHash(passwordEncoder.encode(request.getPasswordHash()));
        tenantAdmin.setEnabled(request.isEnabled());
        tenantAdmin.setUpdatedAt(LocalDateTime.now());
        
        return TenantAdminResponse.fromEntity(tenantAdmin);
    }

    private ManagementUser getTenantAdminEntity(int organizationId) {
        getOrganization(organizationId);
        return managementUserRepository.findByOrganizationId(organizationId)
                .orElseThrow(() -> new ApiException("Tenant administrator not found"));
    }

    private Organization getOrganization(int id) {
        return organizationRepository.findByIdAndStatusNot(id, OrganizationStatus.DELETED)
                .orElseThrow(() -> new ApiException("Organization not found"));
    }

    private void checkUnique(String name, String slug, Integer currentId) {
        if(organizationRepository.existsByNameIgnoreCase(name)) {
            Organization current = currentId == null ?
                    null : organizationRepository.findById(currentId).orElse(null);

            if(current == null || !current.getName().equalsIgnoreCase(name)) {
                throw new ApiException("Organization name already exists");
            }
        }

        if(organizationRepository.existsBySlugIgnoreCase(slug)) {
            Organization current = currentId == null ?
                    null : organizationRepository.findById(currentId).orElse(null);

            if(current == null || !current.getSlug().equalsIgnoreCase(slug)) {
                throw new ApiException("Organization slug already exists");
            }
        }
    }
}