package com.mohamed.jobconnectv2.job;

import com.mohamed.jobconnectv2.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Job {
    @Id
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_id")
    private User employer;
    @Enumerated(EnumType.STRING)
    private JobType jobType;
    private BigDecimal jobBudget;
    private LocalDateTime createdAt;
    private String description;
    private Integer numberOfProposalSubmitted;
    private String location;
    private String industry;
    private String title;

    @Builder
    public Job(UUID id,
               JobType jobType,
               BigDecimal jobBudget,
               LocalDateTime createdAt,
               String description,
               Integer numberOfProposalSubmitted,
               String location,
               String industry,
               String title) {
        this.id = id;
        this.jobType = jobType;
        this.jobBudget = jobBudget;
        this.createdAt = createdAt;
        this.description = description;
        this.numberOfProposalSubmitted = numberOfProposalSubmitted;
        this.location = location;
        this.industry = industry;
        this.title = title;
    }

    public enum JobType{
        PART_TIME,
        FULL_TIME,
        REMOTE
    }
}
