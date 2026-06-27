package com.verbalwala.backend.controller;

import com.verbalwala.backend.dto.response.ApiResponse;
import com.verbalwala.backend.dto.response.AssessmentResultResponse;
import com.verbalwala.backend.service.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentResultController {

    private final EvaluationService evaluationService;

    @GetMapping("/attempts/{attemptId}/result")
    public ResponseEntity<ApiResponse<AssessmentResultResponse>> getResult(
            @PathVariable String attemptId) {

        AssessmentResultResponse result =
                evaluationService.getResult(attemptId);

        return ResponseEntity.ok(
                ApiResponse.<AssessmentResultResponse>builder()
                        .success(true)
                        .message("Result fetched successfully")
                        .data(result)
                        .build()
        );
    }

}
