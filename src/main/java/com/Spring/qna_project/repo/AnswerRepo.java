package com.Spring.qna_project.repo;

import com.Spring.qna_project.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepo extends JpaRepository<Answer,Integer> {

    List<Answer> findByQuestion_QuestionId(Integer questionId);


}
