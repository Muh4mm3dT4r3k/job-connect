package com.mohamed.jobconnectv2.proposal;

import com.mohamed.jobconnectv2.proposal.dto.PendingProposal;
import com.mohamed.jobconnectv2.proposal.dto.ProposalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController("/proposals")
@RequiredArgsConstructor
public class ProposalController {

    private final ProposalService proposalService;

    // TODO handle responses

    @PostMapping("/{jobId}/submit")
    public ResponseEntity<?> submit(@PathVariable UUID jobId) {
        proposalService.submitJob(jobId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/pending")
    @PreAuthorize("hasRole('ROLE_EMPLOYER')")
    public Page<PendingProposal> findAllPendingProposals(@PageableDefault(size = 10) Pageable pageable){
        return proposalService.findAllPendingProposal(pageable);
    }

    @GetMapping("/{proposalId}")
    @PreAuthorize("hasRole('ROLE_EMPLOYER') or authentication.principal.id ==" +
            " @proposalService.findProposalDetails(#proposalId).jobSeekerId()")
    public ProposalDetails findProposals(@PathVariable UUID proposalId){
        return proposalService.findProposalDetails(proposalId);
    }

    @PostMapping("/{proposalId}/{status}/update")
    @PreAuthorize("hasRole('ROLE_EMPLOYER')")
    public ResponseEntity<?> updateProposalStatus(@PathVariable UUID proposalId, @PathVariable ProposalStatus status) {
        proposalService.updateProposalStatus(proposalId, status);
        return ResponseEntity.noContent().build();
    }
}
