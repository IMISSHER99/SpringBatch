package com.project.springBatch.tasklet;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Getter
@Slf4j
@Component
public class HelloTasklet implements Tasklet, StepExecutionListener {

    @Setter
    private StepExecution stepExecution;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        setStepExecution(stepExecution);
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("Hello World!!!!");
        return RepeatStatus.FINISHED;
    }

}
