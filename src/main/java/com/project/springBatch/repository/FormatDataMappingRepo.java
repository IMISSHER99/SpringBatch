package com.project.springBatch.repository;

import com.project.springBatch.constants.QueryConstants;
import com.project.springBatch.entity.ConsumerFileFormatDataMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormatDataMappingRepo extends JpaRepository<ConsumerFileFormatDataMapping, Integer> {

    @Query(QueryConstants.FILE_FORMAT_AND_MAPPING)
    List<Object[]> fetchFileFormatMappingData();
}
