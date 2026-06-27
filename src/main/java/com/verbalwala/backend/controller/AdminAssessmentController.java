package com.verbalwala.backend.controller;

import com.verbalwala.backend.dto.request.CreateAssessmentRequest;
import com.verbalwala.backend.dto.response.ApiResponse;
import com.verbalwala.backend.service.AssessmentService;
import com.verbalwala.backend.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/assessments")
@RequiredArgsConstructor
public class AdminAssessmentController {

    private final AssessmentService assessmentService;
    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createAssessment(
            @Valid @RequestBody CreateAssessmentRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assessmentService.createAssessment(request));
    }
}