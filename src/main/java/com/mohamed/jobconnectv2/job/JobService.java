package com.mohamed.jobconnectv2.job;

import com.mohamed.jobconnectv2.job.dto.PostJobRequest;
import com.mohamed.jobconnectv2.job.dto.PostJobResponse;
import com.mohamed.jobconnectv2.user.User;
import com.mohamed.jobconnectv2.user.UserRepository;
import com.mohamed.jobconnectv2.user.dto.EmployerDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostJobResponse postJob(PostJobRequest request) {
        EmployerDto employer = findEmployerByIdOrThrow(request.employerId());
        User user = getEmployerRef(request.employerId());
        Job job = PostJobRequest.from(request);
        job.setEmployer(user);
        jobRepository.save(job);
        return PostJobResponse.from(job, employer);
    }

    private EmployerDto findEmployerByIdOrThrow(UUID employerId) {
        return userRepository
                .findEmployerById(employerId)
                .orElseThrow(() -> new RuntimeException("employer not found"));
        // TODO handle exception
    }
    private User getEmployerRef(UUID id) {
        return userRepository.getReferenceById(id);
    }
}
