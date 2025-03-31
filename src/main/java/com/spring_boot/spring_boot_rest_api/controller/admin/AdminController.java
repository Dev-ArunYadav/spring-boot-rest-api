package com.spring_boot.spring_boot_rest_api.controller.admin;

import com.spring_boot.spring_boot_rest_api.service.admin.UserFetchService;
import com.spring_boot.spring_boot_rest_api.service.admin.UserUpdateService;
import com.spring_boot.spring_boot_rest_api.utils.APIResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/administrator")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final UserUpdateService userUpdateService;
    private final UserFetchService userFetchService;

    @PutMapping("/update-subscriber/{userId}")
    public ResponseEntity<APIResponse<?>> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest request) {
        logger.info("Received request to update user with ID: {}", userId);
        APIResponse<?> response = userUpdateService.updateUser(userId, request);
        logger.info("Response for updating user with ID {}: {}", userId, response.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/inactive-subscribers")
    public ResponseEntity<?> getAllUsersUnActive() {
        logger.info("Received request to fetch inactive users");
        APIResponse<?> response = userFetchService.getAllUsersUnActive();
        logger.info("Response for fetching inactive users: {}", response.getMessage());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active-subscribers")
    public ResponseEntity<APIResponse<?>> getActiveUsers() {
        logger.info("Received request to fetch active users");
        APIResponse<?> response = userFetchService.getAllActiveUsers();
        logger.info("Response for fetching active users: {}", response.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}