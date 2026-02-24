package com.Spring.qna_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateQuestionRequest {
    private String title;
    private String content;
}
