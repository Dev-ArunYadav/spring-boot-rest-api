package com.spring_boot.spring_boot_rest_api.controller;

import com.spring_boot.spring_boot_rest_api.model.User;
import com.spring_boot.spring_boot_rest_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public StringBuilder registerUser(@Valid @RequestBody User user){
        return userService.registerUser(user);
    }

}
