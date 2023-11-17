package com.project.springBatch;

import com.project.springBatch.constants.JobParameterConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@Slf4j
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

	/**
	 * Command line runner for starting the job
	 * @param args incoming main method arguments
	 * @throws Exception Generic Exception
	 */
	@Override
	public void run(String... args) throws Exception {
		if(args.length < 4) {
			log.warn("Minimum Number of Arguments expected is 4");
			System.exit(-1);
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

		JobExecution jobExecution = jobLauncher.run(job, jobParametersBuilder.toJobParameters());

		if(jobExecution.getStatus().equals(BatchStatus.COMPLETED)) {
			log.info("Job Completed Successfully");
			System.exit(0);
		}
		else {
			log.info("Job Failed");
			System.exit(-1);
		}
	}
}
