package com.project.springBatch.tasklet;

import com.project.springBatch.constants.ExecutionConstants;
import com.project.springBatch.constants.JobParameterConstants;
import com.project.springBatch.entity.ConsumerFileFormatDataMapping;
import com.project.springBatch.utility.QueryBuilder;
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

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Getter
@Component
public class QueryBuilderTasklet implements Tasklet, StepExecutionListener {

    @Setter
    private StepExecution stepExecution;
    private Map<String, Object> mappingData;

    /**
     *
     * @param contribution mutable state to be passed back to update the current step
     * execution
     * @param chunkContext attributes shared between invocations but not between restarts
     * @return tasklet Status
     * @throws Exception Generic Exception
     */
    @Override
    @SuppressWarnings("unchecked")
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("Started Query Builder Tasklet");
        List<ConsumerFileFormatDataMapping> dataMappingList = (List<ConsumerFileFormatDataMapping>) mappingData.get(ConsumerFileFormatDataMapping.class.getName());
        String query = "";
        if(Objects.requireNonNull(stepExecution.getJobExecution().getJobParameters().getString(JobParameterConstants.JOB_TYPE)).equalsIgnoreCase(ExecutionConstants.JSON_DATA)) {
            log.info("JSON Query Builder");
            query = QueryBuilder.jsonQueryBuilder(dataMappingList.get(0).getTableName());
        }
        else {
            log.info("Column Query Builder");
            query = QueryBuilder.defaultQueryBuilder(dataMappingList);
        }
        log.info("Generated Query: {}", query);
        stepExecution.getJobExecution().getExecutionContext().put(ExecutionConstants.QUERY, query);
        return RepeatStatus.FINISHED;
    }

    /**
     *
     * @param stepExecution instance of {@link StepExecution}.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void beforeStep(StepExecution stepExecution) {
        setStepExecution(stepExecution);
        mappingData = Objects.requireNonNull(
                (Map<String, Object>)stepExecution.getJobExecution().getExecutionContext().get(ExecutionConstants.FILE_FORMAT_MAPPING_DATA));

    }
}
