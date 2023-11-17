package com.project.springBatch.tasklet;

import com.project.springBatch.constants.ExecutionConstants;
import com.project.springBatch.constants.JobParameterConstants;
import com.project.springBatch.entity.ConsumerFileFormatDataMapping;
import com.project.springBatch.entity.ConsumerFileFormatMapping;
import com.project.springBatch.entity.ConsumerMaster;
import com.project.springBatch.entity.FileType;
import com.project.springBatch.repository.FormatDataMappingRepo;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Component
public class FormatMappingTasklet implements Tasklet, StepExecutionListener {

    @Setter
    private StepExecution stepExecution;
    private final FormatDataMappingRepo consumerFileFormatDataMappingRepo;

    @Autowired
    public FormatMappingTasklet(FormatDataMappingRepo consumerFileFormatDataMappingRepo) {
        this.consumerFileFormatDataMappingRepo = consumerFileFormatDataMappingRepo;
    }

    /**
     *
     * @param contribution mutable state to be passed back to update the current step
     * execution
     * @param chunkContext attributes shared between invocations but not between restarts
     * @return STATUS
     * @throws Exception GenericException
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("Getting the mapping data from the database");
        String consumerName = stepExecution.getJobExecution().getJobParameters().getString(JobParameterConstants.CONSUMER_NAME);
        String frequency = stepExecution.getJobExecution().getJobParameters().getString(JobParameterConstants.FREQUENCY);
        String processId = stepExecution.getJobExecution().getJobParameters().getString(JobParameterConstants.PROCESS_ID);
        List<Object[]> mappingData = consumerFileFormatDataMappingRepo.fetchFileFormatMappingData(consumerName, processId, frequency);
        Map<String, Object> mappingDataMap = mapDataToObject(mappingData);
        stepExecution.getJobExecution().getExecutionContext().put(ExecutionConstants.FILE_FORMAT_MAPPING_DATA, mappingDataMap);
        log.info("Successfully stored the data into job execution context");
        return RepeatStatus.FINISHED;
    }

    /**
     * Method used to convert the data into a MAP
     * @param mappingData Mapping Data from DB
     * @return Data converted into a map
     */
    public Map<String, Object> mapDataToObject(List<Object[]> mappingData) {
        Map<String, Object> mappingDataMap = new HashMap<>();
        ConsumerFileFormatMapping consumerFileFormatMapping = new ConsumerFileFormatMapping();
        ConsumerMaster consumerMaster = new ConsumerMaster();
        FileType fileType = new FileType();
        BeanUtils.copyProperties(mappingData.get(0)[0], consumerFileFormatMapping);
        List<ConsumerFileFormatDataMapping> dataMappingList = new ArrayList<>();
        for(Object[] data: mappingData) {
            ConsumerFileFormatDataMapping consumerFileFormatDataMapping = new ConsumerFileFormatDataMapping();
            BeanUtils.copyProperties(data[1], consumerFileFormatDataMapping);
            dataMappingList.add(consumerFileFormatDataMapping);
        }
        BeanUtils.copyProperties(mappingData.get(0)[2], consumerMaster);
        BeanUtils.copyProperties(mappingData.get(0)[3], fileType);
        mappingDataMap.put(ConsumerFileFormatMapping.class.getName(), consumerFileFormatMapping);
        mappingDataMap.put(ConsumerFileFormatDataMapping.class.getName(), dataMappingList);
        mappingDataMap.put(ConsumerMaster.class.getName(), consumerMaster);
        mappingDataMap.put(FileType.class.getName(), fileType);
        return mappingDataMap;
    }

    /**
     *
     * @param stepExecution instance of {@link StepExecution}.
     */
    @Override
    public void beforeStep(StepExecution stepExecution) {
        setStepExecution(stepExecution);
    }
}
