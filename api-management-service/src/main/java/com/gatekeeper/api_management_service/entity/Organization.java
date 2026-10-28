package com.gatekeeper.api_management_service.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table(name = "organizations")
@Getter 
@Setter 
@NoArgsConstructor
public class Organization {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Pattern(
    regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$",
    message = "Slug must contain lowercase letters, numbers, and single hyphens only"
    )
    private String slug;

    @Enumerated(EnumType.STRING)
    private OrganizationStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
