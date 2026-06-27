package com.verbalwala.backend.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AssessmentSubmittedResponse {

    private String attemptId;

    private String message;

}