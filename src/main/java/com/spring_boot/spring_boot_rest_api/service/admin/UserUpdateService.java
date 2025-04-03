package com.spring_boot.spring_boot_rest_api.service.admin;

import com.spring_boot.spring_boot_rest_api.controller.admin.UserUpdateRequest;
import com.spring_boot.spring_boot_rest_api.exception.UserNotFoundException;
import com.spring_boot.spring_boot_rest_api.model.User;
import com.spring_boot.spring_boot_rest_api.repository.UserRepository;
import com.spring_boot.spring_boot_rest_api.utils.APIResponse;
import com.spring_boot.spring_boot_rest_api.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUpdateService {

    private static final Logger logger = LoggerFactory.getLogger(UserUpdateService.class);
    private final UserRepository userRepository;

    public APIResponse<?> updateUser(Long userId, UserUpdateRequest request) {
        logger.info("Attempting to update user with ID: {}", userId);

        // Call the custom query to update status and financial year
        int rowsAffected = userRepository.updateUserStatusAndFinancialYear(
                userId, request.getStatus(), request.getFinancialYear()
        );

        // If no rows are affected, it means the user ID does not exist
        if (rowsAffected == 0) {
            logger.error("User with ID {} not found for update", userId);
            throw new UserNotFoundException("User not found");
        }

        logger.info("User with ID {} successfully updated", userId);
        return ResponseUtil.buildSuccessResponse("User updated successfully", null);
    }
}