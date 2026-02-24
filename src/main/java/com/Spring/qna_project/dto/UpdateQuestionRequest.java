package com.Spring.qna_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateQuestionRequest {
    private String title;
    private String content;
}
