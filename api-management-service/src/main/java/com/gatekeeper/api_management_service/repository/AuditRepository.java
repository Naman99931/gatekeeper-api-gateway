package com.gatekeeper.api_management_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gatekeeper.api_management_service.entity.ControlPlaneAuditRecords;
import com.gatekeeper.api_management_service.entity.Organization;

public interface AuditRepository extends JpaRepository<ControlPlaneAuditRecords, Long> {

  List<ControlPlaneAuditRecords> findAllByOrganizationAndResourceTypeAndResourceIdOrderByCreatedAtDesc(
      Organization organization, String string, String resourceId);

  List<ControlPlaneAuditRecords> findAllByOrganizationIdOrderByCreatedAtDesc(Long id);
}
