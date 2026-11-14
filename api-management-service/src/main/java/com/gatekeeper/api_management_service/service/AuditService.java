package com.gatekeeper.api_management_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gatekeeper.api_management_service.entity.ControlPlaneAuditRecords;
import com.gatekeeper.api_management_service.entity.Organization;
import com.gatekeeper.api_management_service.repository.AuditRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service 
public class AuditService {
  private final AuditRepository auditRepository;

  public AuditService(AuditRepository auditRepository) {
    this.auditRepository = auditRepository;
  }

  public void record(Organization organization, String action,
                       String resourceType, String resourceId,
                      long startedAt, String details) {

      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      String actor = authentication == null ? "system" : authentication.getName();

      ControlPlaneAuditRecords record = new ControlPlaneAuditRecords();
      record.setOrganization(organization);
      record.setActor(actor);
      record.setAction(action);
      record.setResourceType(resourceType);
      record.setResourceId(resourceId);
      record.setCorrelationId(getCorrelationId());
      record.setDurationMs((System.nanoTime() - startedAt) / 1000000);
      record.setResult("SUCCESS");
      record.setDetails(details);
      record.setCreatedAt(LocalDateTime.now());

      auditRepository.save(record);
  }

  private String getCorrelationId() {
      if(!(RequestContextHolder.getRequestAttributes()
              instanceof ServletRequestAttributes)) {
          return null;
      }

      HttpServletRequest request = ((ServletRequestAttributes)
              RequestContextHolder.getRequestAttributes())
              .getRequest();
      return request.getHeader("X-Correlation-ID");
  }

  public List<ControlPlaneAuditRecords> getAuditRecordsForResource(Organization organization, String string,
      String resourceId) {
      return auditRepository.findAllByOrganizationAndResourceTypeAndResourceIdOrderByCreatedAtDesc(organization, string, resourceId);
  }
}
