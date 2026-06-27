package com.verbalwala.backend.dto.response;

import com.verbalwala.backend.entity.Question;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StartAssessmentResponse {

    private String attemptId;

    private boolean resumed;

    private Integer fillBlankTime;

    private Integer passageReadTime;

    private Integer passageWriteTime;

    private Integer emailWritingTime;

    private List<QuestionResponse> fillBlankQuestions;

}