package com.verbalwala.backend.controller;

import com.verbalwala.backend.service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class GeminiController {

    private final GeminiService geminiService;

    @GetMapping("/gemini")
    public String testGemini() {

        return geminiService.askGemini(
                "Reply with only one word: Connected"
        );

    }

}