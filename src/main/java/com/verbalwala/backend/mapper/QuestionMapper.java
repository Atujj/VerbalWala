package com.verbalwala.backend.mapper;

import com.verbalwala.backend.dto.request.CreateQuestionRequest;
import com.verbalwala.backend.dto.response.QuestionResponse;
import com.verbalwala.backend.entity.Question;

public class QuestionMapper {

    private QuestionMapper() {
    }

    public static Question toEntity(
            CreateQuestionRequest request,
            String assessmentId
    ) {

        return Question.builder()
                .assessmentId(assessmentId)
                .type(request.getType())
                .questionText(request.getQuestionText())
                .expectedAnswer(request.getExpectedAnswer())
                .alternativeAnswers(request.getAlternativeAnswers())
                .marks(request.getMarks())
                .questionOrder(request.getQuestionOrder())
                .build();
    }

    public static QuestionResponse toResponse(Question question) {

        return QuestionResponse.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .questionOrder(question.getQuestionOrder())
                .build();
    }
}