package com.verbalwala.backend.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AssessmentResultResponse {

    private Integer overallScore;

    private Integer totalMarks;

    private Double percentage;

    private List<QuestionResultResponse> results;

}