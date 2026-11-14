package com.gatekeeper.api_management_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gatekeeper.api_management_service.entity.Organization;
import com.gatekeeper.api_management_service.entity.OrganizationStatus;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Organization findBySlug(String slug);

    List<Organization> findAllByStatusNot(OrganizationStatus status);

    Optional<Organization> findByIdAndStatusNot(Long id, OrganizationStatus deleted);

    boolean existsByNameIgnoreCase(String name);

    boolean existsBySlugIgnoreCase(String slug);
}
