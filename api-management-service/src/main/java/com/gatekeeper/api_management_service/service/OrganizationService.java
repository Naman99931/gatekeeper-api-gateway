package com.gatekeeper.api_management_service.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gatekeeper.api_management_service.dto.requestDto.OrganizationRequest;
import com.gatekeeper.api_management_service.dto.responseDto.OrganizationResponse;
import com.gatekeeper.api_management_service.entity.Organization;
import com.gatekeeper.api_management_service.entity.OrganizationStatus;
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
    public OrganizationResponse updateOrganizationActiveStatus(int id) {
        Organization organization = organizationRepository.findById(id);
        if (organization == null) {
            return null;
        }
        organization.setStatus(OrganizationStatus.ACTIVE);
        organization.setUpdatedAt(LocalDateTime.now());
        organizationRepository.save(organization);
        return OrganizationResponse.fromEntity(organization);
    }
}
