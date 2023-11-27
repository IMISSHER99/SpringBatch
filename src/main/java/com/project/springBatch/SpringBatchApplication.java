package com.project.springBatch;

import com.project.springBatch.config.RunnerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SpringBatchApplication implements CommandLineRunner {


	private final RunnerConfig runnerConfig;

	@Autowired
	public SpringBatchApplication(RunnerConfig runnerConfig) {
		this.runnerConfig = runnerConfig;
	}


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
		int responseCode = runnerConfig.executeJob(args);
		System.exit(responseCode);
	}
}
