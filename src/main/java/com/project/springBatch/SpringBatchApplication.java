package com.project.springBatch;

import com.project.springBatch.constants.JobParameterConstants;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class SpringBatchApplication implements CommandLineRunner {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	@Qualifier("sampleJob")
	private Job job;


	public static void main(String[] args) {
		SpringApplication.run(SpringBatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		JobParameters jobParameters = new JobParametersBuilder().addLocalDateTime(JobParameterConstants.START_TIME, LocalDateTime.now()).toJobParameters();
		JobExecution jobExecution = jobLauncher.run(job, jobParameters);
		if(jobExecution.getStatus().equals(BatchStatus.COMPLETED)) System.exit(0);
		else System.exit(1);
	}
}
