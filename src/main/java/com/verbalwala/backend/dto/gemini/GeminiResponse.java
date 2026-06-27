package com.verbalwala.backend.dto.gemini;

import lombok.Getter;

import java.util.List;

@Getter
public class GeminiResponse {

    private List<Candidate> candidates;

}