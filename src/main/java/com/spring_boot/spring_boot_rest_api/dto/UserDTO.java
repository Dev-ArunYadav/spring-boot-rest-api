package com.spring_boot.spring_boot_rest_api.dto;

import com.spring_boot.spring_boot_rest_api.enums.UserRoles;
import com.spring_boot.spring_boot_rest_api.enums.UserStatus;
import com.spring_boot.spring_boot_rest_api.model.Address;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UserDTO {
    private String fullName;
    private String email;
    private String phone;
    private String profession;
    private UserRoles role;
    private UserStatus status;
    private String financialYear;
    private Address address;
}
