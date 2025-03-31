package com.spring_boot.spring_boot_rest_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @Email @NotNull String email,
        @NotNull String password)
{ }
