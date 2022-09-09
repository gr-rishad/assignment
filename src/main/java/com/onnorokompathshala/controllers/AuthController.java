package com.onnorokompathshala.controllers;

import com.onnorokompathshala.Dto.JWTAuthResponse;
import com.onnorokompathshala.Dto.LoginDTO;
import com.onnorokompathshala.Dto.SignupDTO;
import com.onnorokompathshala.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDTO loginDTO) {
        return userService.signInUser(loginDTO);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupDTO signupDTO) {
        return userService.registerUser(signupDTO);
    }
}
