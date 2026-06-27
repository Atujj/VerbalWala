package com.verbalwala.backend.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionResponse {

    private String id;

    private String questionText;

    private Integer questionOrder;
}