package com.verbalwala.backend.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailResponse {

    private String questionId;

    private String questionText;

    private Integer writingTime;

}