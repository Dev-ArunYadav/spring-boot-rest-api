package com.spring_boot.spring_boot_rest_api.service;

import com.spring_boot.spring_boot_rest_api.dto.LoginRequest;
import com.spring_boot.spring_boot_rest_api.enums.UserRoles;
import com.spring_boot.spring_boot_rest_api.enums.UserStatus;
import com.spring_boot.spring_boot_rest_api.model.User;
import com.spring_boot.spring_boot_rest_api.repository.UserRepository;
import com.spring_boot.spring_boot_rest_api.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public void registerUser(User user) {
        // Hash Password
        user.getUserCredentials().setHashedPassword(
                passwordEncoder.encode(user.getUserCredentials().getHashedPassword())
        );
        user.setStatus(UserStatus.INACTIVE);
        user.setRole(UserRoles.USER);
        // Save User
        userRepository.save(user);
    }

    public String loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email());

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + loginRequest.email());
        }

        if (!passwordEncoder.matches(loginRequest.password(), user.getUserCredentials().getHashedPassword())) {
            throw new RuntimeException("Invalid Credentials");
        }

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new RuntimeException("User is not active");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    }

}