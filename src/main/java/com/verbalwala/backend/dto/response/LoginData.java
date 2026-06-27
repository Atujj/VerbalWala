package com.verbalwala.backend.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginData {

    private String token;

    private String fullName;

    private String role;

}