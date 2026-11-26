package com.gatekeeper.smart_gateway_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
public class HeaderRule {
  private Long id;
  private String direction;
  private String action;
  private String headerName;
  private String headerValue;
}
