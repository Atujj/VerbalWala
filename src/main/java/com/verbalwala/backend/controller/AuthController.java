package com.verbalwala.backend.controller;

import com.verbalwala.backend.dto.request.LoginRequest;
import com.verbalwala.backend.dto.request.SignupRequest;
import com.verbalwala.backend.dto.response.ApiResponse;
import com.verbalwala.backend.dto.response.LoginData;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.verbalwala.backend.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(
            @Valid @RequestBody SignupRequest request){

        ApiResponse response = authService.signup(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginData>> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }

}