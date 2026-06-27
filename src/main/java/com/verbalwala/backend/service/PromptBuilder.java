package com.verbalwala.backend.service;

import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {

    public String buildPassagePrompt(
            String passage,
            String studentAnswer
    ) {

        return """
                You are an expert English evaluator.

                Evaluate the student's passage summary.

                Score each criterion from 0 to 10.

                Return ONLY valid JSON.

                JSON FORMAT:

                {
                  "overallScore":0,
                  "criteria":{
                    "grammar":0,
                    "vocabulary":0,
                    "coherence":0,
                    "relevance":0
                  },
                  "feedback":[
                    "",
                    "",
                    ""
                  ]
                }

                Passage:

                %s

                Student Summary:

                %s
                """.formatted(
                passage,
                studentAnswer
        );

    }

    public String buildEmailPrompt(
            String emailPrompt,
            String studentAnswer
    ) {

        return """
                You are an expert business English evaluator.

                Evaluate the student's professional email.

                Score each criterion from 0 to 10.

                Return ONLY valid JSON.

                JSON FORMAT:

                {
                  "overallScore":0,
                  "criteria":{
                    "grammar":0,
                    "vocabulary":0,
                    "professionalTone":0,
                    "format":0,
                    "taskCompletion":0
                  },
                  "feedback":[
                    "",
                    "",
                    ""
                  ]
                }

                Email Task:

                %s

                Student Email:

                %s
                """.formatted(
                emailPrompt,
                studentAnswer
        );

    }

}