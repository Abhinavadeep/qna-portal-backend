package com.Spring.qna_project.repo;

import com.Spring.qna_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    public List<User> findByRole(String role);

    boolean existsByRole(String role);

    Optional<User> findByEmail(String email);
}
