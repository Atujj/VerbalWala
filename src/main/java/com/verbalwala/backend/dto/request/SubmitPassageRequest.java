package com.verbalwala.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitPassageRequest {

    @NotBlank(message = "Passage answer is required")
    private String answer;

}