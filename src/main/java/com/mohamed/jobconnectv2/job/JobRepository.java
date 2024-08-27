package com.mohamed.jobconnectv2.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {
    @Query("""
    SELECT j FROM Job j JOIN j.employer emp WHERE j.status = com.mohamed.jobconnectv2.job.Job.JobStatus.PENDING
    """)
    List<Job> findAllPendingJobs();

    @Query("""
    SELECT j FROM Job j JOIN j.employer emp WHERE j.id = :jobId AND j.status = com.mohamed.jobconnectv2.job.Job.JobStatus.PENDING
    """)
    Optional<Job> findPendingJobById(UUID jobId);
}