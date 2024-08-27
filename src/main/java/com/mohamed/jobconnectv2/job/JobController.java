package com.mohamed.jobconnectv2.job;

import com.mohamed.jobconnectv2.job.dto.PostJobRequest;
import com.mohamed.jobconnectv2.job.dto.PostJobResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;


    @PostMapping("/post")
    @PreAuthorize("hasRole('ROLE_EMPLOYER')")
    public PostJobResponse postJob(@RequestBody @Valid PostJobRequest request) {
        return jobService.postJob(request);
    }

    @GetMapping("/pending-jobs")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Job> pendingJobs(){
        return jobService.findAllPendingJobs();
    }

    @PostMapping("/accept/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> acceptJob(@PathVariable UUID id) {
        jobService.acceptJob(id);
        return ResponseEntity
                .ok("Job accepted successfully");
    }
}
