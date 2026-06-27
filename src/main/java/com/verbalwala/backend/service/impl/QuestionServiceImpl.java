package com.verbalwala.backend.service.impl;

import com.verbalwala.backend.dto.request.CreateQuestionRequest;
import com.verbalwala.backend.dto.response.ApiResponse;
import com.verbalwala.backend.entity.Question;
import com.verbalwala.backend.enums.QuestionType;
import com.verbalwala.backend.exception.AssessmentNotFoundException;
import com.verbalwala.backend.exception.InvalidQuestionException;
import com.verbalwala.backend.mapper.QuestionMapper;
import com.verbalwala.backend.repository.AssessmentRepository;
import com.verbalwala.backend.repository.QuestionRepository;
import com.verbalwala.backend.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final AssessmentRepository assessmentRepository;
    private final QuestionRepository questionRepository;

    @Override
    public ApiResponse<Void> addQuestion(
            String assessmentId,
            CreateQuestionRequest request) {

        if (!assessmentRepository.existsById(assessmentId)) {
            throw new AssessmentNotFoundException("Assessment not found");
        }

        if (request.getType() == QuestionType.FILL_BLANK &&
                (request.getExpectedAnswer() == null ||
                        request.getExpectedAnswer().isBlank())) {

            throw new InvalidQuestionException(
                    "Expected answer is required for Fill Blank questions");
        }

        Question question =
                QuestionMapper.toEntity(request, assessmentId);

        questionRepository.save(question);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Question added successfully")
                .build();
    }
}