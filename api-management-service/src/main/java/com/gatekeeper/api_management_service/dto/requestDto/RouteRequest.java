package com.gatekeeper.api_management_service.dto.requestDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class RouteRequest {
  @NotBlank 
  @Size(max = 100)
  private String routeId;

  @NotBlank 
  @Size(max = 200)
  private String name;

  @NotBlank 
  @Size(max= 500)
  private String pathPattern;

  private String targetBaseUrl;

  private String targetPathPrefix;

  private int requestsPerMinute;
  
  private int responseTimeoutMs;

  private boolean idempotancyEnabled;

  private boolean active;

  @NotEmpty
  private Set<@NotBlank String> allowedMethods
          = new HashSet<String>();

  @NotNull
  private List<@Valid HeaderRuleRequest> headerRules
          = new ArrayList<HeaderRuleRequest>();
}
