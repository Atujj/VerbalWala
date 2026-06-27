package com.verbalwala.backend.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubmitFillBlankRequest {

    @Valid
    @NotEmpty(message = "Answers are required")
    private List<FillBlankAnswerRequest> answers;

}