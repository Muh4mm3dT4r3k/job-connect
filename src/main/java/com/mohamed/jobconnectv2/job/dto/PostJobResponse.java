package com.mohamed.jobconnectv2.job.dto;

import com.mohamed.jobconnectv2.job.Job;
import com.mohamed.jobconnectv2.job.Job.JobStatus;
import com.mohamed.jobconnectv2.job.Job.JobType;
import com.mohamed.jobconnectv2.user.dto.EmployerDto;
import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record PostJobResponse(
        UUID id,
        JobType type,
        BigDecimal budget,
        LocalDateTime createdAt,
        String description,
        Integer numberOfProposalSubmitted,
        String location,
        String industry,
        String title,
        JobStatus status,
        EmployerDto employer
) implements Serializable {
    public static PostJobResponse from(Job job, EmployerDto employer) {
        return PostJobResponse
                .builder()
                .id(job.getId())
                .type(job.getType())
                .description(job.getDescription())
                .numberOfProposalSubmitted(job.getNumberOfProposalSubmitted())
                .location(job.getLocation())
                .industry(job.getIndustry())
                .title(job.getTitle())
                .status(job.getStatus())
                .employer(employer)
                .createdAt(job.getCreatedAt())
                .build();
    }
}