package com.gatekeeper.api_management_service.repository;

import com.gatekeeper.api_management_service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
    Role findById(int id);
}
