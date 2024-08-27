package com.mohamed.jobconnectv2.job.dto;

import com.mohamed.jobconnectv2.job.Job;
import com.mohamed.jobconnectv2.job.Job.JobStatus;
import com.mohamed.jobconnectv2.job.Job.JobType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


public record PostJobRequest(
        @NotNull
        UUID employerId,
        @NotNull(message = "job type is required")
        JobType type,
        @NotNull(message = "job budget is required")
        @Positive(message = "job budget should be a positive number")
        BigDecimal budget,
        @NotBlank(message = "description is required")
        String description,
        @NotBlank(message = "location is required")
        String location,
        @NotBlank(message = "industry is required")
        String industry,
        @NotBlank(message = "title is required")
        String title
) implements Serializable {
        public static Job from(PostJobRequest request) {
                return Job
                        .builder()
                        .id(UUID.randomUUID())
                        .budget(request.budget())
                        .description(request.description())
                        .numberOfProposalSubmitted(0)
                        .title(request.title())
                        .location(request.location())
                        .industry(request.industry())
                        .type(request.type())
                        .status(JobStatus.PENDING)
                        .createdAt(LocalDateTime.now())
                        .build();
        }
}