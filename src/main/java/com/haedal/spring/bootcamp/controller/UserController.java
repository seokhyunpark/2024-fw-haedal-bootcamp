package com.haedal.spring.bootcamp.controller;

import com.haedal.spring.bootcamp.domain.User;
//import com.haedal.spring.bootcamp.dto.request.UserUpdateRequestDto;
import com.haedal.spring.bootcamp.dto.request.UserRegistrationRequestDto;
//import com.haedal.spring.bootcamp.dto.response.UserDetailResponseDto;
import com.haedal.spring.bootcamp.dto.response.UserSimpleResponseDto;
import com.haedal.spring.bootcamp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<UserSimpleResponseDto> registerUser(@RequestBody UserRegistrationRequestDto userRegistrationRequestDto) {
        User user = new User(
                userRegistrationRequestDto.getUsername(),
                userRegistrationRequestDto.getPassword(),
                userRegistrationRequestDto.getName()
        );
        UserSimpleResponseDto savedUser = userService.saveUser(user);

        return ResponseEntity.ok(savedUser);
    }
}