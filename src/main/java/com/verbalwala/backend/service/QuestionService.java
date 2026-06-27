package com.verbalwala.backend.service;

import com.verbalwala.backend.dto.request.CreateQuestionRequest;
import com.verbalwala.backend.dto.response.ApiResponse;

public interface QuestionService {

    ApiResponse<Void> addQuestion(
            String assessmentId,
            CreateQuestionRequest request
    );

}