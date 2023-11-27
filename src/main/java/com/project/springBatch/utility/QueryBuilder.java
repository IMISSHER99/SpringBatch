package com.project.springBatch.utility;

import com.project.springBatch.constants.QueryConstants;
import com.project.springBatch.entity.ConsumerFileFormatDataMapping;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class QueryBuilder {

    /**
     * JSON query builder
     * @param tableName Table Name
     * @return Query
     */
    public static String jsonQueryBuilder(String tableName) {
        StringBuilder query = new StringBuilder();
        query.append(QueryConstants.JSON_QUERY_BUILDER);
        query.append(tableName);
        return query.toString();
    }

    /**
     * Default Query Builder
     * @param dataMappingList dataMappingList
     * @return Query
     */
    public static String defaultQueryBuilder(List<ConsumerFileFormatDataMapping> dataMappingList) {
        StringBuilder query = new StringBuilder();
        query.append(QueryConstants.SELECT);
        String columnName = dataMappingList.get(dataMappingList.size() - 1).getColumnName();
        String tableName = dataMappingList.get(dataMappingList.size() - 1).getTableName();
        dataMappingList.forEach(mappingData -> {
            query.append(mappingData.getColumnName());
            if(!mappingData.getColumnName().equalsIgnoreCase(columnName)) query.append(", ");
        });
        query.append(QueryConstants.FROM);
        query.append(tableName);
        return query.toString();
    }
}