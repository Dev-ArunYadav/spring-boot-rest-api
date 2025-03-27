package com.spring_boot.spring_boot_rest_api.service;

import com.spring_boot.spring_boot_rest_api.dto.UserDTO;
import com.spring_boot.spring_boot_rest_api.enums.UserRoles;
import com.spring_boot.spring_boot_rest_api.enums.UserStatus;
import com.spring_boot.spring_boot_rest_api.model.User;
import com.spring_boot.spring_boot_rest_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
    /**
     * Register a new user
     *
     * @param user the user to register
     * @return a message indicating the result of the registration
     */
    @Transactional(
            rollbackFor = Exception.class
    )
    public StringBuilder registerUser(User user) {
        StringBuilder builder = new StringBuilder();
        // Check if the user already exists
        Optional<User> existingWithPhone = userRepository.findByPhone(user.getPhone());
        Optional<User> existingWithEmail = userRepository.findByEmail(user.getEmail());

        if (existingWithPhone.isPresent()) {
            return builder.append("Phone number already exists");
        }

        if (existingWithEmail.isPresent()){
            return builder.append("Email already exists");
        }

        if (builder.isEmpty()){
            user.setRole(UserRoles.USER);
            user.setStatus(UserStatus.INACTIVE);

            if (user.getAddress() != null) {
                user.getAddress().setUser(user);
            }

            if (user.getUserCredentials() != null) {
                user.getUserCredentials()
                    .setHashedPassword(passwordEncoder.encode(user.getUserCredentials().getHashedPassword()
                    ));
                user.getUserCredentials().setUser(user);
            }
            userRepository.save(user);
        }
        // Save the user
        return builder.append("Register SuccessFully");
    }
}
