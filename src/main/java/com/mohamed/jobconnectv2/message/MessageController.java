package com.mohamed.jobconnectv2.message;

import com.mohamed.jobconnectv2.message.dto.MessageResponse;
import com.mohamed.jobconnectv2.message.dto.SendMessageRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    @PostMapping
    public void sendMessage(@RequestBody @Valid SendMessageRequest request) {
        messageService.send(request);
    }


    // TODO handle this with AOP
    @GetMapping("{userId}")
    @PreAuthorize("@proposalService.findProposalStatusByJobSeekerId(T(com.mohamed.jobconnectv2.security.SecurityUtils).currentUserId) == T(com.mohamed.jobconnectv2.proposal.ProposalStatus).ACCEPTED")
    public Page<MessageResponse> getMessages(@PathVariable UUID userId, Pageable pageable) {
        return messageService.getMessages(userId, pageable);
    }
}
