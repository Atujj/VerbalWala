package com.verbalwala.backend.service.impl;

import com.verbalwala.backend.dto.request.CreateAssessmentRequest;
import com.verbalwala.backend.dto.response.ApiResponse;
import com.verbalwala.backend.entity.Assessment;
import com.verbalwala.backend.enums.AssessmentStatus;
import com.verbalwala.backend.repository.AssessmentRepository;
import com.verbalwala.backend.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AssessmentServiceImpl implements AssessmentService {


    private final AssessmentRepository assessmentRepository;

    @Override
    public ApiResponse<String> createAssessment(CreateAssessmentRequest request) {

        Assessment assessment = Assessment.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(AssessmentStatus.DRAFT)
                .maxAttempts(request.getMaxAttempts())
                .fillBlankTime(request.getFillBlankTime())
                .passageReadTime(request.getPassageReadTime())
                .passageWriteTime(request.getPassageWriteTime())
                .emailWritingTime(request.getEmailWritingTime())
                .createdById("TEMP_ADMIN")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        assessment = assessmentRepository.save(assessment);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Assessment created successfully")
                .data(assessment.getId())
                .build();
    }
}