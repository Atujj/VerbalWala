package com.verbalwala.backend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAssessmentRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @Min(1)
    private Integer maxAttempts;

    private Integer fillBlankTime;

    private Integer passageReadTime;

    private Integer passageWriteTime;

    private Integer emailWritingTime;

}