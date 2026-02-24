package com.Spring.qna_project.controller;

import com.Spring.qna_project.dto.AdminApplicationResponse;
import com.Spring.qna_project.dto.AdminDetailResponse;
import com.Spring.qna_project.service.AdminApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {

@Autowired
private AdminApplicationService adminApplicationService;

@GetMapping("/applications/pending")
public ResponseEntity<List<AdminApplicationResponse>> getPendingApplications(){
    return ResponseEntity.ok(adminApplicationService.getPendingApplications());
}

@GetMapping("/admins")
public ResponseEntity<List<AdminDetailResponse>> getAdmins(){
    return ResponseEntity.ok(adminApplicationService.getApprovedAdmins());
}

}
