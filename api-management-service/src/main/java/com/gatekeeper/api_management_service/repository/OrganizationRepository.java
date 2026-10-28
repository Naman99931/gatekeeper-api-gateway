package com.gatekeeper.api_management_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gatekeeper.api_management_service.entity.Organization;
import com.gatekeeper.api_management_service.entity.OrganizationStatus;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    Organization findBySlug(String slug);

    Organization findById(int id);

    List<Organization> findAllByStatusNot(OrganizationStatus status);
}
