package com.project.springBatch.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "file_type", schema = "filegen")
public class FileType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_type_id")
    private Integer fileTypeId;

    @OneToMany(mappedBy = "fileType")
    private List<ConsumerFileFormatMapping> consumerFileFormatMappings;

    @Column(name = "description")
    private String description;

    @Column(name = "file_delimiter")
    private String fileDelimiter;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_timestamp")
    private Timestamp createTimestamp;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_timestamp")
    private Timestamp updatedTimestamp;

    @Column(name = "file_extension")
    private String fileExtension;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type")
    private com.project.springBatch.constants.FileType fileType;

    @Override
    public String toString() {
        return "FileType{" +
                "fileTypeId=" + fileTypeId +
                ", description='" + description + '\'' +
                ", fileDelimiter='" + fileDelimiter + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createTimestamp=" + createTimestamp +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", updatedTimestamp=" + updatedTimestamp +
                ", fileExtension='" + fileExtension + '\'' +
                ", fileType=" + fileType +
                '}';
    }
}
