package com.mohamed.jobconnectv2.user;

import com.mohamed.jobconnectv2.user.dto.CreateUserProfileRequest;
import com.mohamed.jobconnectv2.user.dto.UpdateUserProfileRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;

    @PostMapping("/{id}/profile")
    @PreAuthorize("#id == authentication.details.id")
    public ResponseEntity<?> create(@PathVariable UUID id, @RequestBody @Valid CreateUserProfileRequest request) {
       userProfileService.create(id, request);
        return ResponseEntity
                .ok()
                .build();
    }

    @PutMapping("/{id}/profile")
    @PreAuthorize("#id == authentication.details.id")
    public ResponseEntity<?> update(@PathVariable UUID id, @ModelAttribute @Valid UpdateUserProfileRequest request) {
        userProfileService.update(id, request);
        return ResponseEntity.ok().build();
    }
}
