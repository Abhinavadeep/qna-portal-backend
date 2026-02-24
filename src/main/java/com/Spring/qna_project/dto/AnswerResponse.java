package com.Spring.qna_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AnswerResponse {

    private Integer answerId;
    private String content;
    private String author;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
