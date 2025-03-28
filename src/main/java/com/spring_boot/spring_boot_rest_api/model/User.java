package com.spring_boot.spring_boot_rest_api.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring_boot.spring_boot_rest_api.enums.UserRoles;
import com.spring_boot.spring_boot_rest_api.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table( name = "user",
        indexes = {
                @Index(name = "idx_email", columnList = "email", unique = true),
                @Index(name = "idx_phone", columnList = "phone", unique = true)
        }
)
@lombok.Getter @lombok.Setter
@lombok.NoArgsConstructor @lombok.AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Full name is required")
    private String fullName;
    @NotBlank(message = "Auth name is required")
    private String authName;

    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+(com|in|org|net)$", message = "Email must be a valid format and contain @ and a valid domain")
    private String email;
    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[789]\\d{9}$", message = "Phone number is not valid")
    private String phone;
    @NotBlank(message = "Profession is required")
    private String profession;
    private String financialYear;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "credentials_id")
    private UserCredentials userCredentials;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    private UserRoles role;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime registeredAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
