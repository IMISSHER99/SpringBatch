package com.project.springBatch.utility;

import com.project.springBatch.entity.ConsumerFileFormatDataMapping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QueryBuilderTest {

    private final static String MOCK = "MOCK";
    private final static String JSON_QUERY = "SELECT CONTENT FROM MOCK";
    private final static String DEFAULT_QUERY = "SELECT MOCK FROM MOCK";

    @Test
    void testJsonQueryBuilder() {
        Assertions.assertEquals(JSON_QUERY, QueryBuilder.jsonQueryBuilder(MOCK));
    }

    @Test
    void testDefaultQueryBuilder() {
        List<ConsumerFileFormatDataMapping> dataMappingList = new ArrayList<>();
        ConsumerFileFormatDataMapping dataMapping = new ConsumerFileFormatDataMapping();
        dataMapping.setColumnName(MOCK);
        dataMapping.setTableName(MOCK);
        dataMappingList.add(dataMapping);
        Assertions.assertEquals(DEFAULT_QUERY, QueryBuilder.defaultQueryBuilder(dataMappingList));
    }

}