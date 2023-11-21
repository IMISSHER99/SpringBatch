package com.project.springBatch.repository;

import com.project.springBatch.constants.QueryConstants;
import com.project.springBatch.entity.ConsumerFileFormatDataMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormatDataMappingRepo extends JpaRepository<ConsumerFileFormatDataMapping, Integer> {

    /**
     * File format mapping query
     * @param consumerName consumer name
     * @param processId process id
     * @param frequency frequency
     * @return Data
     */
    @Query(QueryConstants.FILE_FORMAT_AND_MAPPING)
    List<Object[]> fetchFileFormatMappingData(@Param("consumer_name") String consumerName, @Param("process_id") String processId, @Param("frequency") String frequency);
}
