package com.mohamed.jobconnectv2.common;

import com.mohamed.jobconnectv2.job.Job;
import com.mohamed.jobconnectv2.job.JobRepository;
import com.mohamed.jobconnectv2.proposal.Proposal;
import com.mohamed.jobconnectv2.proposal.ProposalRepository;
import com.mohamed.jobconnectv2.role.Role;
import com.mohamed.jobconnectv2.role.RoleRepository;
import com.mohamed.jobconnectv2.user.User;
import com.mohamed.jobconnectv2.user.UserProfile;
import com.mohamed.jobconnectv2.user.UserProfileRepository;
import com.mohamed.jobconnectv2.user.UserRepository;

import java.util.UUID;


public class CommonMethods {
    
    public static User findUserByIdOrThrow(UUID userId, UserRepository userRepository) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));
        // TODO handle exception
    }

    public static Role findRoleByName(String role, RoleRepository roleRepository) {
        return roleRepository
                .findByName(role)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        // TODO handle exception
    }

    public static void checkUsernameAlreadyExist(String username, UserRepository userRepository) {
        boolean existsUserByUsername = userRepository.existsUserByUsername(username);
        if (existsUserByUsername)
            throw new RuntimeException("username already exists");
        //TODO handle exception
    }

    public static void checkEmailAlreadyExist(String email, UserRepository userRepository) {
        boolean existsUserByEmail = userRepository.existsUserByEmail(email);
        if (existsUserByEmail)
            throw new RuntimeException("email already exists");
        // TODO handle exception
    }

    public static Job findJobByIdOrThrow(UUID jobId, JobRepository jobRepository) {
        return jobRepository
                .findById(jobId)
                .orElseThrow();
        // TODO handle exception
    }

    public static UserProfile findUserProfileOrThrow(UUID userId, UserProfileRepository userProfileRepository) {
        return userProfileRepository
                .findUserProfileByUserId(userId)
                .orElseThrow();
        // TODO handle exception
    }

    public static Proposal findProposalByIdOrThrow(UUID proposalId, ProposalRepository proposalRepository) {
        return proposalRepository
                .findById(proposalId)
                .orElseThrow();
        // TODO handle exception
    }
}
