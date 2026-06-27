package com.verbalwala.backend.dto.gemini;

import lombok.Getter;

import java.util.List;

@Getter
public class ContentResponse {

    private List<PartResponse> parts;

}