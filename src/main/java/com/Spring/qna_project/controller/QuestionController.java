package com.Spring.qna_project.controller;

import com.Spring.qna_project.dto.CreateQuestionRequest;
import com.Spring.qna_project.dto.QuestionResponse;
import com.Spring.qna_project.dto.UpdateQuestionRequest;
import com.Spring.qna_project.model.Question;
import com.Spring.qna_project.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/questions")
    public ResponseEntity<List<QuestionResponse>> getAllQuestions(){
        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    @GetMapping("/question/{id}")
    public ResponseEntity<QuestionResponse> getQuestionById(@PathVariable Integer id){
        return ResponseEntity.ok(questionService.getQuestionById(id));
    }

    @PostMapping("/questions")
    public ResponseEntity<QuestionResponse> createQuestion(@RequestBody CreateQuestionRequest request, Authentication authentication){
        return ResponseEntity.ok(questionService.createQuestion(request,authentication));
    }

    @PutMapping("/question/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<QuestionResponse> updateQuestion(@PathVariable Integer id, @RequestBody UpdateQuestionRequest request){
        return ResponseEntity.ok(questionService.updateQuestion(id,request));
    }

    @DeleteMapping("/question/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer id){
        questionService.deleteQuestion(id);
        return ResponseEntity.ok("Deleted Sucessfully");
    }

    @GetMapping("/questionsOfUser")
    public ResponseEntity<List<QuestionResponse>> getQuestionsOfUser(Authentication authentication){
        return ResponseEntity.ok(questionService.getQuestionsOfUser(authentication));
    }
}
