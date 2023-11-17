package com.project.springBatch.entity;

import com.project.springBatch.constants.DataType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "consumer_file_format_data_mapping", schema = "filegen")
public class ConsumerFileFormatDataMapping implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "format_mapping_id")
    private Integer formatMappingId;

    @ManyToOne
    @JoinColumn(name = "consumer_format_id", referencedColumnName = "consumer_format_id")
    private ConsumerFileFormatMapping consumerFileFormatMapping;

    @Column(name = "table_name", length = 100, nullable = false)
    private String tableName;

    @Column(name = "column_name", length = 100, nullable = false)
    private String columnName;

    @Column(name = "column_order", nullable = false)
    private Integer columnOrder;

    @Column(name = "column_type", length = 100, nullable = false)
    private String columnType;

    @Column(name = "column_length")
    private Integer columnLength;

    @Column(name = "column_fixed_value", length = 100)
    private String columnFixedValue;

    @Column(name = "column_filler", length = 100)
    private String columnFiller;

    @Column(name = "column_description", length = 300, nullable = false)
    private String columnDescription;

    @Column(name = "is_mandatory")
    private Boolean isMandatory = false;

    @Column(name = "created_by", length = 255, nullable = false)
    private String createdBy;

    @Column(name = "created_timestamp", nullable = false)
    private Timestamp createdTimestamp;

    @Column(name = "modified_by", length = 255)
    private String modifiedBy;

    @Column(name = "last_modified_timestamp", nullable = false)
    private Timestamp lastModifiedTimestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "column_data_type")
    private DataType columnDataType;

    @Column(name = "sort_keys", length = 255, nullable = false)
    private String sortKeys;


    @Override
    public String toString() {
        return "ConsumerFileFormatDataMapping{" +
                "formatMappingId=" + formatMappingId +
                ", consumerFileFormatMapping=" + consumerFileFormatMapping +
                ", tableName='" + tableName + '\'' +
                ", columnName='" + columnName + '\'' +
                ", columnOrder=" + columnOrder +
                ", columnType='" + columnType + '\'' +
                ", columnLength=" + columnLength +
                ", columnFixedValue='" + columnFixedValue + '\'' +
                ", columnFiller='" + columnFiller + '\'' +
                ", columnDescription='" + columnDescription + '\'' +
                ", isMandatory=" + isMandatory +
                ", createdBy='" + createdBy + '\'' +
                ", createdTimestamp=" + createdTimestamp +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", lastModifiedTimestamp=" + lastModifiedTimestamp +
                ", columnDataType=" + columnDataType +
                ", sortKeys='" + sortKeys + '\'' +
                '}';
    }
}
