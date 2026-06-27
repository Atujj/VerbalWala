package com.verbalwala.backend.entity;

import com.verbalwala.backend.enums.AttemptEndReason;
import com.verbalwala.backend.enums.AttemptStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "assessment_attempts")
public class AssessmentAttempt {

    @Id
    private String id;

    private String assessmentId;

    private String studentId;

    private Integer attemptNumber;

    private AttemptStatus status;

    private AttemptEndReason endReason;

    private Integer totalMarks;

    private Integer obtainedMarks;

    private LocalDateTime startedAt;

    private LocalDateTime submittedAt;

}