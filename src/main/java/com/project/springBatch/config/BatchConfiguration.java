package com.project.springBatch.config;

import com.project.springBatch.tasklet.FormatMappingTasklet;
import com.project.springBatch.tasklet.HelloTasklet;
import com.project.springBatch.tasklet.QueryBuilderTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing(tablePrefix = "filegen.BATCH_")
public class BatchConfiguration {

    private final HelloTasklet helloTasklet;
    private final FormatMappingTasklet fileFormatMappingTasklet;
    private final QueryBuilderTasklet queryBuilderTasklet;

    @Autowired
    public BatchConfiguration(HelloTasklet helloTasklet, FormatMappingTasklet fileFormatMappingTasklet, QueryBuilderTasklet queryBuilderTasklet) {
        this.helloTasklet = helloTasklet;
        this.fileFormatMappingTasklet = fileFormatMappingTasklet;
        this.queryBuilderTasklet = queryBuilderTasklet;
    }

    /**
     * Demo Tasklet that logs Hello World!!
     * @param jobRepository Default JobRepository
     * @param platformTransactionManager Default TransactionManager
     * @return Step
     */
    @Bean
    public Step helloTaskletStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("helloTaskletStep", jobRepository)
                .tasklet(helloTasklet, platformTransactionManager)
                .build();
    }

    /**
     * Tasklet that is responsible for getting the file format mapping data
     * and storing it into the job execution context
     * @param jobRepository Default JobRepository
     * @param platformTransactionManager Default TransactionManager
     * @return Step
     */
    @Bean
    public Step fileFormatMappingStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("fileFormatMappingStep", jobRepository)
                .tasklet(fileFormatMappingTasklet, platformTransactionManager)
                .build();
    }

    /**
     * Tasklet that is responsible for preparing the query
     * @param jobRepository Default JobRepository
     * @param platformTransactionManager Default PlatformTransactionManager
     * @return Step
     */
    @Bean
    public Step queryBuilderStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("queryBuilderStep", jobRepository)
                .tasklet(queryBuilderTasklet, platformTransactionManager)
                .build();
    }

    /**
     * JOB configuration for executing the steps
     * @param jobRepository Default JobRepository
     * @param helloTaskletStep HelloTasklet
     * @param fileFormatMappingStep FileFormatMappingTasklet
     * @return Job
     */
    @Bean
    public Job sampleJob(JobRepository jobRepository,
                         @Qualifier("helloTaskletStep") Step helloTaskletStep,
                         @Qualifier("fileFormatMappingStep") Step fileFormatMappingStep,
                         @Qualifier("queryBuilderStep") Step queryBuilderStep) {
        return new JobBuilder("sampleJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(helloTaskletStep)
                .next(fileFormatMappingStep)
                .next(queryBuilderStep)
                .build();
    }
}
