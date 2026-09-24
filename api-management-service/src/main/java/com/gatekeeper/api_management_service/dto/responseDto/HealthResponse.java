package com.gatekeeper.api_management_service.dto.responseDto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
@AllArgsConstructor 
public class HealthResponse {
    private String serviceStatus;
    private String dbStatus;
    private LocalDateTime timeStamp;
}
