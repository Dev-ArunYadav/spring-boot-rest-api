package com.spring_boot.spring_boot_rest_api.controller;

import com.spring_boot.spring_boot_rest_api.dto.UserDTO;
import com.spring_boot.spring_boot_rest_api.model.User;
import com.spring_boot.spring_boot_rest_api.service.UserService;
import com.spring_boot.spring_boot_rest_api.utils.APIResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<APIResponse<UserDTO>> registerUser(@Valid @RequestBody User user){

        return ResponseEntity.ok(userService.registerUser(user));
    }
    /**
     * Login a user
     *
     * @param user the user to login
     * @return a message indicating the result of the login
     */
    @PostMapping("/login")
    public ResponseEntity<APIResponse<UserDTO>> loginUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.loginUser(user));
    }


}
