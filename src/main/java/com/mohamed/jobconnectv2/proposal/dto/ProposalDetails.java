package com.mohamed.jobconnectv2.proposal.dto;

import com.mohamed.jobconnectv2.proposal.ProposalStatus;
import org.springframework.core.io.Resource;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProposalDetails(
        UUID proposalId,
        UUID jobId,
        UUID jobSeekerId,
        ProposalStatus proposalStatus,
        Resource cv,
        LocalDateTime submittedAt
) {
}
