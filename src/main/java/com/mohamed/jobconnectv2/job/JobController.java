package com.mohamed.jobconnectv2.job;

import com.mohamed.jobconnectv2.job.dto.PostJobRequest;
import com.mohamed.jobconnectv2.job.dto.PostJobResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
