package com.Spring.qna_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminApplicationResponse {

    private Integer applicationId;
    private  String username;
    private String email;
    private String status;

}
