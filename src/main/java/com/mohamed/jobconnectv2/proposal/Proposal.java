package com.mohamed.jobconnectv2.proposal;

import com.mohamed.jobconnectv2.job.Job;
import com.mohamed.jobconnectv2.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Proposal {
    @Id
    private UUID id;
    @OneToOne
    private User jobSeeker;
    private String jobSeekerCv;
    @OneToOne
    private Job job;
    @Enumerated(EnumType.STRING)
    private ProposalStatus proposalStatus;
    private LocalDateTime submittedAt;
}
