package com.verbalwala.backend.service;

import com.verbalwala.backend.dto.request.CreateAssessmentRequest;
import com.verbalwala.backend.dto.response.ApiResponse;

public interface AssessmentService {

    ApiResponse<String> createAssessment(CreateAssessmentRequest request);

}