package com.Spring.qna_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicationDecisionRequest {
    private Integer adminId;
    private String action;
}
