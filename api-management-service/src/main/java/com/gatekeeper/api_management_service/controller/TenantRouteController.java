package com.gatekeeper.api_management_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gatekeeper.api_management_service.dto.requestDto.RouteRequest;
import com.gatekeeper.api_management_service.dto.requestDto.RouteStatusRequest;
import com.gatekeeper.api_management_service.dto.responseDto.AuditRecordResponse;
import com.gatekeeper.api_management_service.dto.responseDto.RouteResponse;
import com.gatekeeper.api_management_service.dto.responseDto.RouteVersionResponse;
import com.gatekeeper.api_management_service.service.RouteService;

import jakarta.validation.Valid;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController 
@RequestMapping("/api/tenant")
public class TenantRouteController {
  private final RouteService routeService;
  public TenantRouteController(RouteService routeService) {
    this.routeService = routeService;
  }

  @PostMapping("/routes")
  public ResponseEntity<RouteResponse> createRoute(@Valid @RequestBody RouteRequest request) {
      return ResponseEntity.status(HttpStatus.CREATED)
                .body(routeService.createRoute(request));
  }
  
  @GetMapping("/routes")
  public ResponseEntity<List<RouteResponse>> findAll() {
      return ResponseEntity.ok(routeService.findAll());
  }
  
  @GetMapping("/routes/{id}")
  public ResponseEntity<RouteResponse> findById(@PathVariable("id") Long id) {
      return ResponseEntity.ok(routeService.findById(id));
  }

  @PutMapping("/routes/{id}")
  public ResponseEntity<RouteResponse> updateRoute(@PathVariable Long id, @RequestBody RouteRequest request) {
      return ResponseEntity.status(HttpStatus.OK)
                .body(routeService.updateRoute(id, request));
  }

  @PatchMapping("/routes/{id}/status")
  public ResponseEntity<RouteResponse> changeStatus(@PathVariable Long id, @Valid @RequestBody RouteStatusRequest request) {
      return ResponseEntity.ok(routeService.changeStatus(id, request));
  }

  @DeleteMapping ("/routes/{id}")
  public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
      routeService.deleteRoute(id);
      return ResponseEntity.noContent().build();
  }

  @GetMapping("/route-versions/{routeId}")
  public ResponseEntity<List<RouteVersionResponse>> getVersions(
          @PathVariable String routeId) {
      return ResponseEntity.ok(routeService.getVersions(routeId));
  }

  @GetMapping("/audit-records")
  public ResponseEntity<List<AuditRecordResponse>> getAuditRecords() {
      return ResponseEntity.ok(routeService.getAuditRecords());
  }
}
