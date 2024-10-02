package com.haedal.spring.bootcamp.controller;

import com.haedal.spring.bootcamp.domain.User;
import com.haedal.spring.bootcamp.dto.request.LoginRequestDto;
import com.haedal.spring.bootcamp.dto.request.UserRegistrationRequestDto;
import com.haedal.spring.bootcamp.dto.response.UserSimpleResponseDto;
import com.haedal.spring.bootcamp.service.AuthService;
import com.haedal.spring.bootcamp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
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

    @PostMapping("/auth/login")
    public ResponseEntity<UserSimpleResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) {
        UserSimpleResponseDto userSimpleResponseDto = authService.login(loginRequestDto, request);
        return ResponseEntity.ok(userSimpleResponseDto);
    }


    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        authService.logout(request);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/auth/me")
    public ResponseEntity<UserSimpleResponseDto> me(HttpServletRequest request) {
        User currentUser = authService.getCurrentUser(request);

        UserSimpleResponseDto userSimpleResponseDto = userService.convertUserToSimpleDto(currentUser, currentUser);
        return ResponseEntity.ok(userSimpleResponseDto);
    }
}
