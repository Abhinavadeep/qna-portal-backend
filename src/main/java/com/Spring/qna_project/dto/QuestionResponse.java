package com.Spring.qna_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {
    private Integer questionId;
    private String title;
    private String content;
    private String author;
    private String status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
