package com.verbalwala.backend.entity;

import com.verbalwala.backend.enums.QuestionType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "questions")
public class Question {

    @Id
    private String id;

    private String assessmentId;

    private QuestionType type;

    // Sentence / Passage / Email Prompt
    private String questionText;

    // Only for Fill Blank
    private String expectedAnswer;

    // Optional alternative answers
    @Builder.Default
    private List<String> alternativeAnswers = new ArrayList<>();

    private Integer marks;

    private Integer questionOrder;

}