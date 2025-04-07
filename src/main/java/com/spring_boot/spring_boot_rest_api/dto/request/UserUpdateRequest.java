package com.spring_boot.spring_boot_rest_api.dto.request;

import com.spring_boot.spring_boot_rest_api.enums.UserStatus;

public record UserUpdateRequest (
    String financialYear,
    UserStatus status
){
}

