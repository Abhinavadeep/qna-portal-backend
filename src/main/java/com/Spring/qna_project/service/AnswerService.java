package com.Spring.qna_project.service;

import com.Spring.qna_project.dto.AnswerResponse;
import com.Spring.qna_project.dto.CreateAnswerRequest;
import com.Spring.qna_project.dto.UpdateAnswerRequest;
import com.Spring.qna_project.model.Answer;
import com.Spring.qna_project.model.Question;
import com.Spring.qna_project.model.User;
import com.Spring.qna_project.repo.AnswerRepo;
import com.Spring.qna_project.repo.QuestionRepo;
import com.Spring.qna_project.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepo answerRepo;

    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private UserRepo userRepo;

    public List<AnswerResponse> getAnswersByQuestionId(Integer id) {
        return answerRepo.findByQuestion_QuestionId(id)
                .stream()
                .map(a->new AnswerResponse(
                        a.getAnswerId(),
                        a.getContent(),
                        a.getAuthor().getEmail(),
                        a.getCreatedDate(),
                        a.getUpdatedDate()
                )).toList();
    }

    public AnswerResponse createAnswer(
            Integer questionId,
            CreateAnswerRequest request,
            Authentication authentication
    ) {

        String email = authentication.getName();  // Extracted from JWT

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Question question = questionRepo.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        Answer answer = new Answer();
        answer.setContent(request.getContent());
        answer.setAuthor(user);
        answer.setQuestion(question);
        answer.setCreatedDate(LocalDateTime.now());
        answer.setUpdatedDate(null);

        Answer saved = answerRepo.save(answer);

        return new AnswerResponse(
                saved.getAnswerId(),
                saved.getContent(),
                saved.getAuthor().getEmail(),
                saved.getCreatedDate(),
                saved.getUpdatedDate()
        );
    }


    public AnswerResponse updateAnswer(
            Integer id,
            UpdateAnswerRequest request,
            Authentication authentication
    ) {

        String email = authentication.getName();

        Answer answer = answerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Answer not found"));

        User loggedUser = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!answer.getAuthor().getEmail().equals(email)
                && !loggedUser.getRole().equals("ADMIN")) {

            throw new RuntimeException("You are not authorized to edit this answer");
        }

        answer.setContent(request.getContent());
        answer.setUpdatedDate(LocalDateTime.now());

        Answer updated = answerRepo.save(answer);

        return new AnswerResponse(
                updated.getAnswerId(),
                updated.getContent(),
                updated.getAuthor().getEmail(),   // since you're using email comparison
                updated.getCreatedDate(),
                updated.getUpdatedDate()
        );
    }


    public void deleteAnswer(Integer id) {
        Answer answer = answerRepo.findById(id).get();
        answerRepo.delete(answer);
    }
}
