package com.Spring.qna_project.service;

import com.Spring.qna_project.dto.CreateQuestionRequest;
import com.Spring.qna_project.dto.QuestionResponse;
import com.Spring.qna_project.dto.UpdateQuestionRequest;
import com.Spring.qna_project.model.Question;
import com.Spring.qna_project.model.User;
import com.Spring.qna_project.repo.QuestionRepo;
import com.Spring.qna_project.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private UserRepo userRepo;

    public List<QuestionResponse> getAllQuestions() {
        return questionRepo.findAll().stream()
                .map(q-> new QuestionResponse(
                        q.getQuestionId(),
                        q.getTitle(),
                        q.getContent(),
                        q.getAuthor().getUsername(),
                        q.getStatus(),
                        q.getCreatedDate(),
                        q.getUpdatedDate()
                ))
                .toList();
    }

    public QuestionResponse getQuestionById(Integer id) {
        Question q = questionRepo.findById(id).get();
        return new QuestionResponse(
                q.getQuestionId(),
                q.getTitle(),
                q.getContent(),
                q.getAuthor().getUsername(),
                q.getStatus(),
                q.getCreatedDate(),
                q.getUpdatedDate()
        );
    }

    public QuestionResponse createQuestion(CreateQuestionRequest request, Authentication authentication){

        String email = authentication.getName();
        User user = userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("User not found Exception"));
        Question question = new Question();
        question.setAuthor(user);
        question.setTitle(request.getTitle());
        question.setContent(request.getContent());
        question.setStatus("active");
        question.setCreatedDate(LocalDateTime.now());
        question.setUpdatedDate(null);

        Question saved = questionRepo.save(question);

        return new QuestionResponse(
                saved.getQuestionId(),
                saved.getTitle(),
                saved.getContent(),
                saved.getAuthor().getUsername(),
                saved.getStatus(),
                saved.getCreatedDate(),
                saved.getUpdatedDate()
        );
    }

    public QuestionResponse updateQuestion(Integer id, UpdateQuestionRequest request) {

        Question question = questionRepo.findById(id).get();
        question.setTitle(request.getTitle());
        question.setContent(request.getContent());
        question.setUpdatedDate(LocalDateTime.now());

        Question updated = questionRepo.save(question);

        return new QuestionResponse(
                updated.getQuestionId(),
                updated.getTitle(),
                updated.getContent(),
                updated.getAuthor().getUsername(),
                updated.getStatus(),
                updated.getCreatedDate(),
                updated.getUpdatedDate()
        );
    }

    public void deleteQuestion(Integer id) {
        Question question = questionRepo.findById(id).get();
        questionRepo.delete(question);
    }

    public List<QuestionResponse> getQuestionsOfUser(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("User not found!!"));
        List<QuestionResponse> list = questionRepo.findById(user.getUserID())
                .stream()
                .map(q->new QuestionResponse(q.getQuestionId(),
                        q.getTitle(),
                        q.getContent(),
                        q.getAuthor().getUsername(),
                        q.getStatus(),
                        q.getCreatedDate(),
                        q.getUpdatedDate())).toList();
        return list;
    }
}
