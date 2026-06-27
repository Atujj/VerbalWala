package com.verbalwala.backend.service.impl;

import com.verbalwala.backend.dto.request.FillBlankAnswerRequest;
import com.verbalwala.backend.dto.request.SubmitEmailRequest;
import com.verbalwala.backend.dto.request.SubmitFillBlankRequest;
import com.verbalwala.backend.dto.request.SubmitPassageRequest;
import com.verbalwala.backend.dto.response.*;
import com.verbalwala.backend.entity.*;
import com.verbalwala.backend.enums.*;
import com.verbalwala.backend.exception.AssessmentNotFoundException;
import com.verbalwala.backend.exception.InvalidAssessmentException;
import com.verbalwala.backend.mapper.QuestionMapper;
import com.verbalwala.backend.repository.*;
import com.verbalwala.backend.service.EvaluationService;
import com.verbalwala.backend.service.StudentAssessmentService;
import com.verbalwala.backend.service.StudentSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.verbalwala.backend.entity.Assessment;
import com.verbalwala.backend.entity.User;
import com.verbalwala.backend.enums.AssessmentStatus;
import com.verbalwala.backend.enums.QuestionType;
import com.verbalwala.backend.exception.AssessmentNotFoundException;
import com.verbalwala.backend.exception.InvalidAssessmentException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.security.cert.Extension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentAssessmentServiceImpl implements StudentAssessmentService {

    private final AssessmentRepository assessmentRepository;

    private final QuestionRepository questionRepository;

    private final AssessmentAttemptRepository assessmentAttemptRepository;

    private final UserRepository userRepository;

    private final StudentAnswerRepository studentAnswerRepository;

    private final EvaluationService evaluationService;

    private final StudentSecurityService studentSecurityService;

    @Override
    public ApiResponse<StartAssessmentResponse> startAssessment(String assessmentId) {

        User student = studentSecurityService.getCurrentStudent();

        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() ->
                        new AssessmentNotFoundException("Assessment not found"));

        if (assessment.getStatus() != AssessmentStatus.PUBLISHED) {
            throw new InvalidAssessmentException(
                    "Assessment is not available");
        }

        Optional<AssessmentAttempt> existingAttempt =
                assessmentAttemptRepository.findByAssessmentIdAndStudentIdAndStatus(
                        assessmentId,
                        student.getId(),
                        AttemptStatus.STARTED
                );

        AssessmentAttempt attempt;

        boolean resumed = false;

        if (existingAttempt.isPresent()) {

            attempt = existingAttempt.get();
            resumed = true;

        } else {

            Optional<AssessmentAttempt> latestAttempt =
                    assessmentAttemptRepository
                            .findTopByAssessmentIdAndStudentIdOrderByAttemptNumberDesc(
                                    assessmentId,
                                    student.getId()
                            );

            int nextAttempt = latestAttempt
                    .map(a -> a.getAttemptNumber() + 1)
                    .orElse(1);

            if (nextAttempt > assessment.getMaxAttempts()) {
                throw new RuntimeException("Maximum attempts exceeded");
            }

            attempt = AssessmentAttempt.builder()
                    .assessmentId(assessmentId)
                    .studentId(student.getId())
                    .attemptNumber(nextAttempt)
                    .status(AttemptStatus.STARTED)
                    .startedAt(LocalDateTime.now())
                    .build();

            assessmentAttemptRepository.save(attempt);
        }

        List<Question> fillBlankQuestions =
                questionRepository.findByAssessmentIdAndTypeOrderByQuestionOrder(
                        assessmentId,
                        QuestionType.FILL_BLANK
                );

        List<QuestionResponse> questionResponses =
                fillBlankQuestions.stream()
                        .map(QuestionMapper::toResponse)
                        .toList();

        StartAssessmentResponse response =
                StartAssessmentResponse.builder()
                        .attemptId(attempt.getId())
                        .resumed(resumed)
                        .fillBlankTime(assessment.getFillBlankTime())
                        .passageReadTime(assessment.getPassageReadTime())
                        .passageWriteTime(assessment.getPassageWriteTime())
                        .emailWritingTime(assessment.getEmailWritingTime())
                        .fillBlankQuestions(questionResponses)
                        .build();

        return ApiResponse.<StartAssessmentResponse>builder()
                .success(true)
                .message(resumed
                        ? "Assessment resumed successfully"
                        : "Assessment started successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<PassageResponse> submitFillBlanks(
            String attemptId,
            SubmitFillBlankRequest request) {

        AssessmentAttempt attempt =
                studentSecurityService.getStudentAttempt(
                        attemptId
                );

        if (attempt.getStatus() != AttemptStatus.STARTED) {
            throw new RuntimeException("Assessment already submitted");
        }

        List<Question> questions =
                questionRepository.findByAssessmentIdAndTypeOrderByQuestionOrder(
                        attempt.getAssessmentId(),
                        QuestionType.FILL_BLANK
                );

        Map<String, Question> questionMap =
                questions.stream()
                        .collect(Collectors.toMap(
                                Question::getId,
                                q -> q
                        ));

        Integer obtainedMarks = 0;

        List<StudentAnswer> studentAnswers = new ArrayList<>();

        for (FillBlankAnswerRequest answerRequest : request.getAnswers()) {

            Question question =
                    questionMap.get(answerRequest.getQuestionId());

            if (question == null) {
                continue;
            }

            boolean correct = isCorrectAnswer(
                    answerRequest.getAnswer(),
                    question
            );

            int marks = correct
                    ? question.getMarks()
                    : 0;

            obtainedMarks += marks;

            StudentAnswer studentAnswer =
                    StudentAnswer.builder()
                            .attemptId(attemptId)
                            .questionId(question.getId())
                            .answer(answerRequest.getAnswer())
                            .obtainedMarks(marks)
                            .evaluationStatus(
                                    EvaluationStatus.AUTO_EVALUATED
                            )
                            .feedback(
                                    correct
                                            ? List.of("Correct answer.")
                                            : List.of("Incorrect answer.")
                            )
                            .build();

            studentAnswers.add(studentAnswer);

        }
        studentAnswerRepository.saveAll(studentAnswers);

        attempt.setObtainedMarks(obtainedMarks);
        assessmentAttemptRepository.save(attempt);

        Question passage =
                questionRepository
                        .findByAssessmentIdAndTypeOrderByQuestionOrder(
                                attempt.getAssessmentId(),
                                QuestionType.PASSAGE
                        )
                        .stream()
                        .findFirst()
                        .orElseThrow(() ->
                                new RuntimeException("Passage not found"));

        Assessment assessment =
                assessmentRepository.findById(
                                attempt.getAssessmentId())
                        .orElseThrow(() ->
                                new AssessmentNotFoundException(
                                        "Assessment not found"));

        PassageResponse response =
                PassageResponse.builder()
                        .questionId(passage.getId())
                        .questionText(passage.getQuestionText())
                        .readingTime(
                                assessment.getPassageReadTime()
                        )
                        .writingTime(
                                assessment.getPassageWriteTime()
                        )
                        .build();

        return ApiResponse.<PassageResponse>builder()
                .success(true)
                .message("Fill Blank section submitted")
                .data(response)
                .build();

    }


    private boolean isCorrectAnswer(
            String studentAnswer,
            Question question
    ) {

        String answer =
                studentAnswer
                        .trim()
                        .toLowerCase();

        if (question.getExpectedAnswer() != null &&
                answer.equals(
                        question.getExpectedAnswer()
                                .trim()
                                .toLowerCase()
                )) {

            return true;
        }

        return question.getAlternativeAnswers()
                .stream()
                .map(a -> a.trim().toLowerCase())
                .anyMatch(answer::equals);

    }

    @Override
    public ApiResponse<EmailResponse> submitPassage(
            String attemptId,
            SubmitPassageRequest request) {

        AssessmentAttempt attempt =
                studentSecurityService.getStudentAttempt(
                        attemptId
                );

        if (attempt.getStatus() != AttemptStatus.STARTED) {
            throw new RuntimeException("Assessment already submitted");
        }

        Assessment assessment =
                assessmentRepository.findById(attempt.getAssessmentId())
                        .orElseThrow(() ->
                                new AssessmentNotFoundException("Assessment not found"));


        Question passage =
                questionRepository
                        .findByAssessmentIdAndTypeOrderByQuestionOrder(
                                attempt.getAssessmentId(),
                                QuestionType.PASSAGE
                        )
                        .stream()
                        .findFirst()
                        .orElseThrow(() ->
                                new RuntimeException("Passage not found"));

        StudentAnswer studentAnswer =
                StudentAnswer.builder()
                        .attemptId(attemptId)
                        .questionId(passage.getId())
                        .answer(request.getAnswer())
                        .obtainedMarks(0)
                        .evaluationStatus(EvaluationStatus.PENDING)
                        .feedback(List.of())
                        .build();

        studentAnswerRepository.save(studentAnswer);

        Question email =
                questionRepository
                        .findByAssessmentIdAndTypeOrderByQuestionOrder(
                                attempt.getAssessmentId(),
                                QuestionType.EMAIL
                        )
                        .stream()
                        .findFirst()
                        .orElseThrow(() ->
                                new RuntimeException("Email question not found"));

        EmailResponse response =
                EmailResponse.builder()
                        .questionId(email.getId())
                        .questionText(email.getQuestionText())
                        .writingTime(assessment.getEmailWritingTime())
                        .build();

        return ApiResponse.<EmailResponse>builder()
                .success(true)
                .message("Passage submitted")
                .data(response)
                .build();

    }

    @Override
    public ApiResponse<AssessmentSubmittedResponse> submitEmail(
            String attemptId,
            SubmitEmailRequest request) {

        AssessmentAttempt attempt =
                studentSecurityService.getStudentAttempt(
                        attemptId
                );

        if (attempt.getStatus() != AttemptStatus.STARTED) {
            throw new RuntimeException("Assessment already submitted");
        }

        Assessment assessment =
                assessmentRepository.findById(attempt.getAssessmentId())
                        .orElseThrow(() ->
                                new AssessmentNotFoundException("Assessment not found"));

        Question email =
                questionRepository
                        .findByAssessmentIdAndTypeOrderByQuestionOrder(
                                attempt.getAssessmentId(),
                                QuestionType.EMAIL
                        )
                        .stream()
                        .findFirst()
                        .orElseThrow(() ->
                                new RuntimeException("Email question not found"));

        StudentAnswer studentAnswer =
                StudentAnswer.builder()
                        .attemptId(attemptId)
                        .questionId(email.getId())
                        .answer(request.getAnswer())
                        .obtainedMarks(0)
                        .evaluationStatus(EvaluationStatus.PENDING)
                        .feedback(List.of())
                        .build();

        studentAnswerRepository.save(studentAnswer);

        attempt.setStatus(AttemptStatus.SUBMITTED);
        attempt.setSubmittedAt(LocalDateTime.now());
        attempt.setEndReason(AttemptEndReason.SUBMITTED);

        assessmentAttemptRepository.save(attempt);
        evaluationService.evaluateAttempt(attemptId);

        AssessmentSubmittedResponse response =
                AssessmentSubmittedResponse.builder()
                        .attemptId(attempt.getId())
                        .message("Assessment submitted successfully. Evaluation in progress.")
                        .build();

        return ApiResponse.<AssessmentSubmittedResponse>builder()
                .success(true)
                .message("Assessment submitted successfully")
                .data(response)
                .build();
    }
}