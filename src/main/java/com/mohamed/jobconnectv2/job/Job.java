package com.mohamed.jobconnectv2.job;

import com.mohamed.jobconnectv2.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE job SET deleted = TRUE WHERE id = ?")
@FilterDef(name = "deletedJobFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedJobFilter", condition = "deleted = :isDeleted")
public class Job {
    @Id
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employerId")
    private User employer;
    @Enumerated(EnumType.STRING)
    private JobType type;
    private BigDecimal budget;
    private LocalDateTime createdAt;
    private String description;
    private Integer numberOfProposalSubmitted;
    private String location;
    private String industry;
    private String title;
    private boolean deleted = Boolean.FALSE;
    @Enumerated(EnumType.STRING)
    private JobStatus status;
    @Builder
    public Job(UUID id,
               JobType type,
               BigDecimal budget,
               LocalDateTime createdAt,
               String description,
               Integer numberOfProposalSubmitted,
               String location,
               String industry,
               String title,
               JobStatus status) {
        this.id = id;
        this.type = type;
        this.budget = budget;
        this.createdAt = createdAt;
        this.description = description;
        this.numberOfProposalSubmitted = numberOfProposalSubmitted;
        this.location = location;
        this.industry = industry;
        this.title = title;
        this.status = status;
    }

    public enum JobType{
        PART_TIME,
        FULL_TIME,
        REMOTE
    }

    public enum JobStatus {
        ACCEPTED,
        REJECTED,
        PENDING
    }
}
