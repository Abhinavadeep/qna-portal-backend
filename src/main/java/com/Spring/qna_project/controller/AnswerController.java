package com.Spring.qna_project.controller;

import com.Spring.qna_project.dto.AnswerResponse;
import com.Spring.qna_project.dto.ApplicationDecisionRequest;
import com.Spring.qna_project.dto.CreateAnswerRequest;
import com.Spring.qna_project.dto.UpdateAnswerRequest;
import com.Spring.qna_project.service.AdminApplicationService;
import com.Spring.qna_project.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AdminApplicationService adminApplicationService;

    @GetMapping("/question/{id}/answers")
    public ResponseEntity<List<AnswerResponse>> getAnswersByQuestionId(@PathVariable Integer id){
        return ResponseEntity.ok(answerService.getAnswersByQuestionId(id));
    }


    @PostMapping("/question/{id}/answers")
    public ResponseEntity<AnswerResponse> createAnswer(
            @PathVariable Integer id,
            @RequestBody CreateAnswerRequest request,
            Authentication authentication) {

        return ResponseEntity.ok(
                answerService.createAnswer(id, request, authentication)
        );
    }


    @PutMapping("/answers/{id}")
    public ResponseEntity<AnswerResponse> updateAnswer(@PathVariable Integer id, @RequestBody UpdateAnswerRequest request,Authentication authentication){
        return ResponseEntity.ok(answerService.updateAnswer(id,request,authentication));
    }

    @DeleteMapping("/answers/{id}")
    public ResponseEntity<String> deleteAnswer(@PathVariable Integer id){
        answerService.deleteAnswer(id);
        return ResponseEntity.ok("Deleted Sucessfully");
    }

    @PostMapping("/applications/{id}")
    public ResponseEntity<String> processApplication(
            @PathVariable Integer id,
            @RequestBody ApplicationDecisionRequest request) {

        adminApplicationService.processApplication(id, request);
        return ResponseEntity.ok("Application processed successfully");
    }

}
