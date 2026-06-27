package com.verbalwala.backend.entity;

import com.verbalwala.backend.enums.EvaluationStatus;
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
@Document(collection = "student_answers")
public class StudentAnswer {

    @Id
    private String id;

    private String attemptId;

    private String questionId;

    private String answer;

    private Integer obtainedMarks;

    private EvaluationStatus evaluationStatus;

    @Builder.Default
    private List<String> feedback = new ArrayList<>();

}