package com.mohamed.jobconnectv2.proposal.dto;

import com.mohamed.jobconnectv2.proposal.ProposalStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record PendingProposal(
        UUID proposalId,
        UUID jobId,
        UUID jobSeekerId,
        ProposalStatus proposalStatus,
        LocalDateTime submittedAt
) {
}
