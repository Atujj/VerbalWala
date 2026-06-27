package com.verbalwala.backend.dto.ai;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EvaluationCriteria {

    private Integer grammar;

    private Integer vocabulary;

    private Integer coherence;

    private Integer relevance;

    private Integer taskCompletion;

    private Integer professionalTone;

}