package com.project.springBatch.config;

import com.project.springBatch.constants.JobParameterConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Slf4j
@Configuration
public class RunnerConfig {

    private final JobLauncher jobLauncher;

    private final Job job;

    @Autowired
    public RunnerConfig(JobLauncher jobLauncher, @Qualifier("sampleJob") Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }


    public int executeJob(String ...args) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        if(args.length < 4) {
            log.warn("Minimum Number of Arguments expected is 4");
            return -1;
        }
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
                .addLocalDateTime(JobParameterConstants.APPLICATION_START_TIME, LocalDateTime.now())
                .addString(JobParameterConstants.CONSUMER_NAME, args[0])
                .addString(JobParameterConstants.PROCESS_ID, args[1])
                .addString(JobParameterConstants.FREQUENCY, args[2])
                .addString(JobParameterConstants.JOB_TYPE, args[3]);

        if(args.length > 4) {
            jobParametersBuilder.addString(JobParameterConstants.START_TIME, args[4])
                    .addString(JobParameterConstants.END_TIME, args[5]);
        }

        JobParameters jobParameters = jobParametersBuilder.toJobParameters();

        JobExecution jobExecution = jobLauncher.run(job, jobParameters);

        if(jobExecution.getStatus().equals(BatchStatus.COMPLETED)) {
            log.info("Job Completed Successfully");
            return 0;
        }

        return -1;

    }
}
