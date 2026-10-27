package com.gatekeeper.api_management_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gatekeeper.api_management_service.entity.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    Organization findBySlug(String slug);

    Organization findById(int id);
}
