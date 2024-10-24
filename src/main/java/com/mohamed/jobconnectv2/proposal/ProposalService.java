package com.mohamed.jobconnectv2.proposal;

import com.mohamed.jobconnectv2.common.CommonMethods;
import com.mohamed.jobconnectv2.files.PdfFileService;
import com.mohamed.jobconnectv2.job.Job;
import com.mohamed.jobconnectv2.job.JobRepository;
import com.mohamed.jobconnectv2.proposal.dto.PendingProposal;
import com.mohamed.jobconnectv2.proposal.dto.ProposalDetails;
import com.mohamed.jobconnectv2.security.SecurityUtils;
import com.mohamed.jobconnectv2.user.User;
import com.mohamed.jobconnectv2.user.UserProfile;
import com.mohamed.jobconnectv2.user.UserProfileRepository;
import com.mohamed.jobconnectv2.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.mohamed.jobconnectv2.proposal.ProposalStatus.PENDING;

@Service
@RequiredArgsConstructor
public class ProposalService {
    private final ProposalRepository proposalRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final PdfFileService pdfFileService;


    @Transactional
    public void submitJob(UUID jobId) {
        Job job = CommonMethods.findJobByIdOrThrow(jobId, jobRepository);
        UUID jobSeekerId = SecurityUtils.getCurrentUserId();
        User jobSeeker = CommonMethods.findUserByIdOrThrow(jobSeekerId, userRepository);
        UserProfile userProfile = CommonMethods.findUserProfileOrThrow(jobSeekerId, userProfileRepository);
        Proposal proposal = new Proposal();
        proposal.setJob(job);
        proposal.setJobSeeker(jobSeeker);
        proposal.setJobSeekerCv(userProfile.getCv());
        proposal.setSubmittedAt(LocalDateTime.now());
        proposal.setId(UUID.randomUUID());
        proposal.setProposalStatus(PENDING);

        job.setNumberOfProposalSubmitted(job.getNumberOfProposalSubmitted() + 1);
        proposalRepository.save(proposal);
    }


    public Page<PendingProposal> findAllPendingProposal(Pageable pageable) {
        return proposalRepository.findAllPendingProposal(pageable);
    }

    public ProposalDetails findProposalDetails(UUID proposalId) {
        Proposal proposal = CommonMethods.findProposalByIdOrThrow(proposalId, proposalRepository);
        Resource cv = pdfFileService.download(proposal.getJobSeekerCv());

        return new ProposalDetails(
                proposal.getId(),
                proposal.getJob().getId(),
                proposal.getJobSeeker().getId(),
                proposal.getProposalStatus(),
                cv,
                proposal.getSubmittedAt()
        );
    }

    @Transactional
    public void updateProposalStatus(UUID proposalId, ProposalStatus proposalStatus) {
        Proposal proposal = CommonMethods.findProposalByIdOrThrow(proposalId, proposalRepository);
        proposal.setProposalStatus(proposalStatus);
        proposalRepository.save(proposal);
    }

    public ProposalStatus findProposalStatusByJobSeekerId(UUID jobSeekerId) {
        return proposalRepository.findByJobSeekerId(jobSeekerId).orElse(ProposalStatus.REJECTED);
    }
}
