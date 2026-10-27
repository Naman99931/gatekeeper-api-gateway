package com.gatekeeper.api_management_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gatekeeper.api_management_service.entity.ManagementUser;

public interface ManagementUserRepository extends JpaRepository<ManagementUser, Integer> {
    java.util.Optional<ManagementUser> findByUsername(String username);
    java.util.Optional<ManagementUser> findById(int id);

}
