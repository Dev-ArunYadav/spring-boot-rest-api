package com.spring_boot.spring_boot_rest_api.service.admin;

import com.spring_boot.spring_boot_rest_api.dto.request.UserUpdateRequest;
import com.spring_boot.spring_boot_rest_api.exception.UserNotFoundException;
import com.spring_boot.spring_boot_rest_api.repository.UserRepository;
import com.spring_boot.spring_boot_rest_api.utils.APIResponse;
import com.spring_boot.spring_boot_rest_api.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserUpdateService {

    private static final Logger logger = LoggerFactory.getLogger(UserUpdateService.class);
    private final UserRepository userRepository;

    @Transactional(
            rollbackFor = Exception.class // Rollback for any exception
    )
    public APIResponse<?> updateUser(Long userId, UserUpdateRequest request) {
        logger.info("Attempting to update user with ID: {}", userId);

        // Call the custom query to update status and financial year
        int rowsAffected = userRepository.updateUserStatusAndFinancialYear(
                userId, request.status(), request.financialYear()
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