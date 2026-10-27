package com.gatekeeper.api_management_service.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table (name = "management_users")
@Getter 
@Setter 
@NoArgsConstructor 
public class ManagementUser {

    @Id 
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "organization_id", referencedColumnName = "id")
    private Organization organization;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "role_id", referencedColumnName = "id")
    private Role role;

    private String username;
    private String passwordHash;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
