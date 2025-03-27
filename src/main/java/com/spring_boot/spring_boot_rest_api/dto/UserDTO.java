package com.spring_boot.spring_boot_rest_api.dto;

import com.spring_boot.spring_boot_rest_api.enums.UserRoles;
import com.spring_boot.spring_boot_rest_api.enums.UserStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class UserDTO {
    private String fullName;
    private String email;
    private String phone;
    private String profession;
    private UserRoles role;
    private UserStatus status;
}
