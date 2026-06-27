package com.verbalwala.backend.dto.request;

import com.verbalwala.backend.enums.QuestionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreateQuestionRequest {

    @NotNull
    private QuestionType type;

    @NotBlank
    private String questionText;

    private String expectedAnswer;

    private List<String> alternativeAnswers = new ArrayList<>();

    @Min(1)
    private Integer marks;

    @Min(1)
    private Integer questionOrder;

}