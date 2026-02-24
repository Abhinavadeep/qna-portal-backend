package com.Spring.qna_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminDetailResponse {

    private Integer userId;
    private String username;
    private String email;
    private String approvedBy;
}

