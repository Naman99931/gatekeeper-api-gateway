package com.gatekeeper.api_management_service.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class OrganizationRequest {

  @NotBlank 
  @Size (min = 3, max = 50)
  private String name;

  @NotBlank 
  @Pattern (regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$", message = "Slug must be lowercase, alphanumeric, and can contain hyphens")
  private String slug;
}
