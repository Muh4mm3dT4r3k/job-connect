package com.mohamed.jobconnectv2.proposal;

import com.mohamed.jobconnectv2.proposal.dto.PendingProposal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ProposalRepository extends JpaRepository<Proposal, UUID> {

    @Query("""
        SELECT new com.mohamed.jobconnectv2.proposal.dto.PendingProposal(p.id, p.job.id, p.jobSeeker.id, p.proposalStatus, p.submittedAt) 
        FROM Proposal p 
        WHERE p.proposalStatus = 'PENDING'
    """)
    Page<PendingProposal> findAllPendingProposal(Pageable pageable);

    @Query("""
    SELECT p.proposalStatus FROM Proposal p WHERE p.jobSeeker.id = :jobSeekerId
    """)
    Optional<ProposalStatus> findByJobSeekerId(UUID jobSeekerId);
}