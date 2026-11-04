package com.gatekeeper.api_management_service.repository;

import com.gatekeeper.api_management_service.entity.Role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findById(int id);
    Optional<Role> findByName(String name);
}
