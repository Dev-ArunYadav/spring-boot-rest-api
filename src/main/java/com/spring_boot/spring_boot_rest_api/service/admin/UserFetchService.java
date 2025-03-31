package com.spring_boot.spring_boot_rest_api.service.admin;

import com.spring_boot.spring_boot_rest_api.dto.UserDTO;
import com.spring_boot.spring_boot_rest_api.enums.UserStatus;
import com.spring_boot.spring_boot_rest_api.exception.UserNotFoundException;
import com.spring_boot.spring_boot_rest_api.model.User;
import com.spring_boot.spring_boot_rest_api.repository.UserRepository;
import com.spring_boot.spring_boot_rest_api.utils.APIResponse;
import com.spring_boot.spring_boot_rest_api.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserFetchService {

    private static final Logger logger = LoggerFactory.getLogger(UserFetchService.class);
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    // Convert User to UserDTO
    private UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private List<UserDTO> convertToDTOList(List<User> users) {
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public APIResponse<?> getAllUsersUnActive() {
        logger.info("Fetching inactive users");
        List<User> users = userRepository.findByStatus(UserStatus.INACTIVE);

        if (users.isEmpty()) {
            logger.warn("No inactive users found");
            throw new UserNotFoundException("No inactive users found");
        }
        // Convert to UserDTO
        List<UserDTO> inactiveUserDTOs = convertToDTOList(users);

        logger.info("Successfully fetched {} inactive users", inactiveUserDTOs.size());
        return ResponseUtil.buildSuccessResponse("Inactive users retrieved successfully", inactiveUserDTOs);
    }

    public APIResponse<?> getAllActiveUsers() {
        logger.info("Fetching active users");
        List<User> activeUsers = userRepository.findByStatus(UserStatus.ACTIVE);

        if (activeUsers.isEmpty()) {
            logger.warn("No active users found");
            throw new UserNotFoundException("No active users found");
        }
        // Convert to UserDTO
        List<UserDTO> activeUserDTOs = convertToDTOList(activeUsers);

        logger.info("Successfully fetched {} active users", activeUserDTOs.size());
        return ResponseUtil.buildSuccessResponse("Active users retrieved successfully", activeUserDTOs);
    }
}
