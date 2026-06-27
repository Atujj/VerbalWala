package com.verbalwala.backend.service;

import com.verbalwala.backend.dto.request.LoginRequest;
import com.verbalwala.backend.dto.request.SignupRequest;
import com.verbalwala.backend.dto.response.ApiResponse;
import com.verbalwala.backend.dto.response.LoginData;


public interface AuthService {

    ApiResponse<Void> signup(SignupRequest request);

    ApiResponse<LoginData> login(LoginRequest request);

}