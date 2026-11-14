package com.gatekeeper.api_management_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gatekeeper.api_management_service.entity.RouteConfigurationVersion;

public interface RouteConfigurationRepository extends JpaRepository<RouteConfigurationVersion, Long> {

  List<RouteConfigurationVersion> findAllByOrganizationIdAndRouteIdOrderByVersionNumberDesc(Long id, String routeId);
  
}
