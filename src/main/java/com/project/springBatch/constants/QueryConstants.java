package com.project.springBatch.constants;

public final class QueryConstants {

    private QueryConstants() {}

    public static final String FILE_FORMAT_AND_MAPPING = "SELECT cffm, cffdm, cm, ft " +
            "FROM ConsumerFileFormatMapping cffm " +
            "JOIN cffm.consumerMaster cm " +
            "JOIN cffm.fileType ft " +
            "JOIN ConsumerFileFormatDataMapping cffdm ON cffm.consumerFormatId = cffdm.consumerFileFormatMapping.consumerFormatId";
}
