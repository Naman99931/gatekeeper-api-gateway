package com.gatekeeper.api_management_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gatekeeper.api_management_service.entity.ManagementUser;

public interface ManagementUserRepository extends JpaRepository<ManagementUser, Integer> {
    Optional<ManagementUser> findByUsername(String username);
    Optional<ManagementUser> findById(int id);
    Optional<ManagementUser> findByOrganizationId(int organizationId);
    boolean existsByUsername(String username);
}
