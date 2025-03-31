package com.spring_boot.spring_boot_rest_api.controller.admin;

import com.spring_boot.spring_boot_rest_api.enums.UserStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    private String financialYear;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
}

