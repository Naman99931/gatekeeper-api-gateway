package com.gatekeeper.api_management_service.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gatekeeper.api_management_service.dto.requestDto.LoginRequestDto;
import com.gatekeeper.api_management_service.dto.responseDto.LoginResponseDto;
import com.gatekeeper.api_management_service.entity.ManagementUser;
import com.gatekeeper.api_management_service.entity.OrganizationStatus;
import com.gatekeeper.api_management_service.repository.ManagementUserRepository;

@Service 
public class AuthService {

    private final ManagementUserRepository managementUserRepository;
    private final JwtEncoder jwtEncoder;
    private final PasswordEncoder passwordEncoder;
    private final long jwtExpiration;

    public AuthService(ManagementUserRepository managementUserRepository,
                        JwtEncoder jwtEncoder, 
                        PasswordEncoder passwordEncoder,
                        @Value ("${security.jwt.expiration-seconds}") long jwtExpiration) {
        this.managementUserRepository = managementUserRepository;
        this.jwtEncoder = jwtEncoder;
        this.passwordEncoder = passwordEncoder;
        this.jwtExpiration = jwtExpiration;
    }

    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto loginRequest) {
            
            ManagementUser user = managementUserRepository
                                    .findByUsername(loginRequest.getUsername())
                                    .orElseThrow(
                                        () -> new RuntimeException("User not found")
                                    );
            
            if(!user.isEnabled() || !passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
                throw new RuntimeException("Invalid credentials");
            }

            if(user.getOrganization() != null && user.getOrganization().getStatus() != OrganizationStatus.ACTIVE) {
                throw new RuntimeException("The organization is not active");
            }

            Instant issuedAt = Instant.now();
            String role = user.getRole().getName();
            Integer organizationId = user.getOrganization() == null ? null : user.getOrganization().getId();

            JwtClaimsSet.Builder claims = JwtClaimsSet.builder()
                                    .issuer("self")
                                    .issuedAt(issuedAt)
                                    .expiresAt(issuedAt.plusSeconds(jwtExpiration))
                                    .subject(user.getUsername())
                                    .claim("role", role);

            if(organizationId != null) {
                claims.claim("organizationId", organizationId);
            }

            JwsHeader jwsHeader = JwsHeader.with(() -> "HS256").build();

            String token = jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims.build())).getTokenValue();

            return new LoginResponseDto(token, "Bearer", jwtExpiration, role, organizationId);
        }
}
