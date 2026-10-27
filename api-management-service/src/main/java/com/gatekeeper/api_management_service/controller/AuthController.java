package com.gatekeeper.api_management_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gatekeeper.api_management_service.dto.requestDto.LoginRequestDto;
import com.gatekeeper.api_management_service.dto.responseDto.LoginResponseDto;
import com.gatekeeper.api_management_service.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController 
@RequestMapping ("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
    
}
