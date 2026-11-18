package com.gatekeeper.api_management_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gatekeeper.api_management_service.entity.ApiRoutes;
import com.gatekeeper.api_management_service.entity.OrganizationStatus;

public interface ApiRouteRepository extends JpaRepository<ApiRoutes, Long> {

  boolean existsByOrganizationIdAndRouteId(Long id, String routeId);

  List<ApiRoutes> findAllByOrganizationId(Long id);

  Optional<ApiRoutes> findByIdAndOrganizationId(Long id, Long organizationId);

  List<ApiRoutes> findAllByActiveTrueAndOrganizationStatus(OrganizationStatus active);

}
