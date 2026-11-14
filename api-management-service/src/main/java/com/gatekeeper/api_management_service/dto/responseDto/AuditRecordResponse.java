package com.gatekeeper.api_management_service.dto.responseDto;

import java.time.LocalDateTime;

import com.gatekeeper.api_management_service.entity.ControlPlaneAuditRecords;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter 
@AllArgsConstructor 
public class AuditRecordResponse {
  private Long id;
  private String actor;
  private String action;
  private String resourceType;
  private String resourceId;
  private String correlationId;
  private Long durationMs;
  private String result;
  private String details;
  private LocalDateTime createdAt;

  public static AuditRecordResponse from(ControlPlaneAuditRecords record) {
      return new AuditRecordResponse(record.getId(), record.getActor(), record.getAction(),
              record.getResourceType(), record.getResourceId(), record.getCorrelationId(),
              record.getDurationMs(), record.getResult(), record.getDetails(), record.getCreatedAt());
  }
}
