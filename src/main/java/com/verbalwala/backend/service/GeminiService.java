package com.verbalwala.backend.service;

import com.verbalwala.backend.dto.ai.AiEvaluationResponse;

public interface GeminiService {

    String askGemini(String prompt);

    AiEvaluationResponse evaluatePassage(
            String passage,
            String studentAnswer
    );

    AiEvaluationResponse evaluateEmail(
            String emailPrompt,
            String studentAnswer
    );

}