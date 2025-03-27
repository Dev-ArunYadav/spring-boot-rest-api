package com.spring_boot.spring_boot_rest_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_credentials")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;
    private String verificationToken;
    private LocalDateTime tokenExpiry;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}

