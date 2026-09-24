package com.gatekeeper.api_management_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gatekeeper.api_management_service.dto.responseDto.HealthResponse;
import com.gatekeeper.api_management_service.service.HealthService;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController 
@RequestMapping("/health")
public class HealthController {
    
    private HealthService healthService;

    public HealthController(HealthService healthService){
        this.healthService = healthService;
    }

    @GetMapping
    public ResponseEntity<HealthResponse> health() {
        String dbStatus = healthService.getHealth();
        return ResponseEntity.ok(
            new HealthResponse("UP", dbStatus, LocalDateTime.now())
        );
    } 
}
