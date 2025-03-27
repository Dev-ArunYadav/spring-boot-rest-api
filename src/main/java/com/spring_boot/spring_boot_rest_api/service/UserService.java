package com.spring_boot.spring_boot_rest_api.service;

import com.spring_boot.spring_boot_rest_api.dto.UserDTO;
import com.spring_boot.spring_boot_rest_api.enums.UserRoles;
import com.spring_boot.spring_boot_rest_api.enums.UserStatus;
import com.spring_boot.spring_boot_rest_api.model.User;
import com.spring_boot.spring_boot_rest_api.repository.UserRepository;
import com.spring_boot.spring_boot_rest_api.utils.APIResponse;
import com.spring_boot.spring_boot_rest_api.utils.ErrorDetails;
import com.spring_boot.spring_boot_rest_api.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Transactional(rollbackFor = Exception.class)
    public APIResponse<UserDTO> registerUser(User user) {

        List<ErrorDetails> errors = checkUserExists(user);
        if (!errors.isEmpty()) {
            return ResponseUtil.buildErrorResponse("User already exists", errors);
        }

        if (user.getAddress() != null) { user.getAddress().setUser(user); }
        if (user.getUserCredentials() != null) {
            user.getUserCredentials().setHashedPassword(
                    passwordEncoder.encode(user.getUserCredentials().getHashedPassword()
                    ));
            user.getUserCredentials().setVerificationToken(UUID.randomUUID().toString());
            user.getUserCredentials().getTokenExpiry().plusSeconds(60 * 60 * 24); // 1 day expiry
            user.getUserCredentials().setUser(user);
        }
        user.setRole(UserRoles.USER);
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);

        // Convert Entity to userDTO
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return  ResponseUtil.buildSuccessResponse("User registered successfully", userDTO);
    }

    private List<ErrorDetails> checkUserExists(User userDTO) {
        List<ErrorDetails> errors = new ArrayList<>();

        if (userRepository.findByPhone(userDTO.getPhone()).isPresent()) {
            errors.add(new ErrorDetails("phone", "Phone number already registered."));
        }
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            errors.add(new ErrorDetails("email", "Email already registered."));
        }
        return errors;
    }

    // delete user
    @Transactional(rollbackFor = Exception.class)
    public APIResponse<String> deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return ResponseUtil.buildSuccessResponse("User deleted successfully", null);
        } else {
            return ResponseUtil.buildErrorResponse("User not found", null);
        }
    }
}