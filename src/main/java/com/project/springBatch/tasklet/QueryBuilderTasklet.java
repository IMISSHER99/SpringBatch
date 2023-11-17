package com.project.springBatch.tasklet;

import com.project.springBatch.constants.ExecutionConstants;
import com.project.springBatch.constants.JobParameterConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Getter
@Component
public class QueryBuilderTasklet implements Tasklet, StepExecutionListener {

    @Setter
    private StepExecution stepExecution;

    private Map<String, Object> mappingData;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void beforeStep(StepExecution stepExecution) {
        setStepExecution(stepExecution);
        mappingData = Objects.requireNonNull(
                (Map<String, Object>)stepExecution.getJobExecution().getExecutionContext().get(ExecutionConstants.FILE_FORMAT_MAPPING_DATA));

    }
}
