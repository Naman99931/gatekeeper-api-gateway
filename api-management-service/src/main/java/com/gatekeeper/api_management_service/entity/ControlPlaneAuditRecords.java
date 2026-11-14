package com.gatekeeper.api_management_service.entity;

import java.time.LocalDateTime;

import org.aspectj.weaver.ast.Or;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table (name = "control_plane_audit_records")
@Getter 
@Setter 
@NoArgsConstructor 
public class ControlPlaneAuditRecords {
  @Id 
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
  @JoinColumn (name = "organization_id", nullable = false)
  private Organization organization;

  private String actor;
  private String action;
  private String resourceType;
  private String resourceId;
  private String correlationId;
  private Long durationMs;
  private String result;
  private String details;
  private LocalDateTime createdAt;
}
