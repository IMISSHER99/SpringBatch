package com.project.springBatch.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class RunnerConfigTest {

    @Mock
    private JobLauncher jobLauncher;
    @Mock
    private JobExecution jobExecution;
    @Mock
    private Job job;


    @Test
    void testExecuteJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        RunnerConfig runnerConfig = new RunnerConfig(jobLauncher, job);
        Mockito.when(jobLauncher.run(any(Job.class), any(JobParameters.class))).thenReturn(jobExecution);
        Mockito.when(jobExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);
        String[] args = {"consumerName", "processId", "frequency", "jobType", "startTime", "endTime"};
        int status = runnerConfig.executeJob(args);
        assertEquals(0, status);
        Mockito.verify(jobLauncher, Mockito.times(1)).run(any(Job.class), any(JobParameters.class));
    }

    @Test
    void testExecuteJob_fails() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        RunnerConfig runnerConfig = new RunnerConfig(jobLauncher, job);
        Mockito.when(jobLauncher.run(any(Job.class), any(JobParameters.class))).thenReturn(jobExecution);
        Mockito.when(jobExecution.getStatus()).thenReturn(BatchStatus.FAILED);
        String[] args = {"consumerName", "processId", "frequency", "jobType", "startTime", "endTime"};
        int status = runnerConfig.executeJob(args);
        assertEquals(-1, status);
        Mockito.verify(jobLauncher, Mockito.times(1)).run(any(Job.class), any(JobParameters.class));
    }

    @Test
    void testExecuteJob_invalidArgumentCount() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        RunnerConfig runnerConfig = new RunnerConfig(jobLauncher, job);
        String[] args = {"consumerName"};
        int status = runnerConfig.executeJob(args);
        assertEquals(-1, status);
    }

}