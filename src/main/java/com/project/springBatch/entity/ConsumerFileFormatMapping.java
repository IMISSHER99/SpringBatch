package com.project.springBatch.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Date;

@Data
@Entity
@Table(name = "consumer_file_format_mapping", schema = "filegen")
public class ConsumerFileFormatMapping implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consumer_format_id")
    private Integer consumerFormatId;

    @ManyToOne
    @JoinColumn(name = "file_type_id", referencedColumnName = "file_type_id")
    private FileType fileType;

    @ManyToOne
    @JoinColumn(name = "consumer_id", referencedColumnName = "consumer_id")
    private ConsumerMaster consumerMaster;


    @Column(name = "mapping_version")
    private Integer mappingVersion;

    @Column(name = "effective_date")
    private Date effectiveDate;

    @Column(name = "termination_date")
    private Date terminationDate;

    @Column(name = "file_has_header")
    private Boolean fileHasHeader;

    @Column(name = "file_has_trailer")
    private Boolean fileHasTrailer;

    @Column(name = "file_header_pattern", length = 255)
    private String fileHeaderPattern;

    @Column(name = "file_trailer_pattern", length = 255)
    private String fileTrailerPattern;

    @Column(name = "date_format", length = 30)
    private String dateFormat;

    @Column(name = "currency_format", length = 30)
    private String currencyFormat;

    @Column(name = "json_version", length = 255)
    private String jsonVersion;

    @Column(name = "extract_selection_config", columnDefinition = "jsonb")
    private String extractSelectionConfig;

    @Column(name = "filename", length = 255, nullable = false)
    private String filename;

    @Column(name = "file_destination", length = 255, nullable = false)
    private String fileDestination;

    @Column(name = "requires_control_file")
    private Boolean requiresControlFile;

    @Column(name = "control_file_config", columnDefinition = "jsonb")
    private String controlFileConfig;

    @Override
    public String toString() {
        return "ConsumerFileFormatMapping{" +
                "consumerFormatId=" + consumerFormatId +
                ", fileType=" + fileType +
                ", consumerMaster=" + consumerMaster +
                ", mappingVersion=" + mappingVersion +
                ", effectiveDate=" + effectiveDate +
                ", terminationDate=" + terminationDate +
                ", fileHasHeader=" + fileHasHeader +
                ", fileHasTrailer=" + fileHasTrailer +
                ", fileHeaderPattern='" + fileHeaderPattern + '\'' +
                ", fileTrailerPattern='" + fileTrailerPattern + '\'' +
                ", dateFormat='" + dateFormat + '\'' +
                ", currencyFormat='" + currencyFormat + '\'' +
                ", jsonVersion='" + jsonVersion + '\'' +
                ", extractSelectionConfig='" + extractSelectionConfig + '\'' +
                ", filename='" + filename + '\'' +
                ", fileDestination='" + fileDestination + '\'' +
                ", requiresControlFile=" + requiresControlFile +
                ", controlFileConfig='" + controlFileConfig + '\'' +
                '}';
    }

}
