package com.gatekeeper.api_management_service.dto.requestDto;

import com.gatekeeper.api_management_service.entity.OrganizationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationStatusRequest {
    @NotNull(message = "Status cannot be null")
    private OrganizationStatus status;
}
