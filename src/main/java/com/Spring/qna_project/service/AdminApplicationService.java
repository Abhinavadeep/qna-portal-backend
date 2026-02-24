package com.Spring.qna_project.service;

import com.Spring.qna_project.dto.AdminApplicationResponse;
import com.Spring.qna_project.dto.AdminDetailResponse;
import com.Spring.qna_project.dto.ApplicationDecisionRequest;
import com.Spring.qna_project.model.AdminApplication;
import com.Spring.qna_project.model.User;
import com.Spring.qna_project.repo.AdminApplicationRepo;
import com.Spring.qna_project.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminApplicationService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AdminApplicationRepo adminApplicationRepo;

    public List<AdminApplicationResponse> getPendingApplications() {
        return adminApplicationRepo.findByStatus("PENDING").stream()
                .map(app -> new AdminApplicationResponse(
                        app.getApplicationId(),
                        app.getUser().getUsername(),
                        app.getUser().getEmail(),
                        app.getStatus()
                )).toList();
    }

    public List<AdminDetailResponse> getApprovedAdmins(){
        return adminApplicationRepo.findByStatus("APPROVED")
                .stream()
                .map(app->new AdminDetailResponse(
                        app.getUser().getUserID(),
                        app.getUser().getUsername(),
                        app.getUser().getEmail(),
                        app.getApprovedBy() != null
                                ? app.getApprovedBy().getUsername()
                                : "System"
                )).toList();
    }

    @Transactional
    public String applyForAdmin(Integer userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🚫 If already admin
        if ("ADMIN".equals(user.getRole())) {
            return "You are already an admin.";
        }

        // 🚫 Prevent duplicate pending applications
        boolean alreadyApplied = adminApplicationRepo
                .existsByUserAndStatus(user, "PENDING");

        if (alreadyApplied) {
            return "You already have a pending application.";
        }

        // 🔥 Check if any admin exists
        boolean adminExists = userRepo.existsByRole("ADMIN");

        if (!adminExists) {
            user.setRole("ADMIN");
            user.setApprovalStatus("APPROVED");
            return "You are the first admin. Approved automatically.";
        }

        AdminApplication application = new AdminApplication();
        application.setUser(user);
        application.setStatus("PENDING");

        adminApplicationRepo.save(application);

        return "Admin application submitted for approval.";
    }


    @Transactional
    public void processApplication(Integer applicationId, ApplicationDecisionRequest request){
        AdminApplication application = adminApplicationRepo.findById(applicationId).get();

        User admin = userRepo.findById(request.getAdminId()).get();
        if (request.getAction().equalsIgnoreCase("APPROVE")) {

            application.setStatus("APPROVED");
            application.setApprovedBy(admin);

            User applicant = application.getUser();
            applicant.setRole("ADMIN");
            applicant.setApprovalStatus("APPROVED");

        } else if (request.getAction().equalsIgnoreCase("REJECT")) {

            application.setStatus("REJECTED");
            application.setApprovedBy(admin);

            User applicant = application.getUser();
            applicant.setApprovalStatus("REJECTED");

        }
    }

}
