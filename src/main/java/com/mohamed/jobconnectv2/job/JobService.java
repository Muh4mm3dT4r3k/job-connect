package com.mohamed.jobconnectv2.job;

import com.mohamed.jobconnectv2.job.Job.JobStatus;
import com.mohamed.jobconnectv2.job.dto.PostJobRequest;
import com.mohamed.jobconnectv2.job.dto.PostJobResponse;
import com.mohamed.jobconnectv2.user.User;
import com.mohamed.jobconnectv2.user.UserRepository;
import com.mohamed.jobconnectv2.user.dto.EmployerDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.mohamed.jobconnectv2.job.Job.JobStatus.ACCEPTED;

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

    @Transactional
    public void acceptJob(UUID jobId) {
        Job job = findJobByIdOrThrow(jobId);
        job.setStatus(ACCEPTED);
        jobRepository.save(job);
    }

    private Job findJobByIdOrThrow(UUID jobId) {
        return jobRepository
                .findPendingJobById(jobId)
                .orElseThrow(() -> new RuntimeException("job not found"));
        // TODO handle exception
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

    public List<Job> findAllPendingJobs() {
        return jobRepository.findAllPendingJobs();
    }
}
