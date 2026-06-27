package com.verbalwala.backend.dto.ai;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AiEvaluationResponse {

    private Integer overallScore;

    private EvaluationCriteria criteria;

    private List<String> feedback;

}