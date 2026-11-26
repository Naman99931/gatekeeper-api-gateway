package com.gatekeeper.smart_gateway_service.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
public class GatewayRoute {
  private Long id;
  private Long organizationId;
  private String routeId;
  private String name;
  private String pathPattern;
  private String targetBaseUrl;
  private String targetPathPrefix;
  private Integer requestsPerMinute;
  private Integer responseTimeoutMs;
  private boolean idempotencyEnabled;
  private boolean active;
  private Long configurationVersion;
  private Set<String> allowedMethods;
  private List<HeaderRule> headerRules;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
