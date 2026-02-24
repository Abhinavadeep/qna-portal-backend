package com.Spring.qna_project.repo;

import com.Spring.qna_project.model.AdminApplication;
import com.Spring.qna_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminApplicationRepo extends JpaRepository<AdminApplication,Integer> {
    List<AdminApplication> findByStatus(String status);
    boolean existsByUserAndStatus(User user, String status);

}
