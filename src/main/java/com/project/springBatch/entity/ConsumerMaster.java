package com.project.springBatch.entity;

import com.project.springBatch.constants.ConsumerType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "consumer_master", schema = "filegen")
public class ConsumerMaster implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consumer_id")
    private Integer consumerId;

    @OneToMany(mappedBy = "consumerMaster")
    private List<ConsumerFileFormatMapping> consumerFileFormatMappings;

    @Column(name = "consumer_name")
    private String consumerName;

    @Column(name = "process_id")
    private String processId;

    @Column(name = "description")
    private String description;
    @Column(name = "frequency")
    private String frequency;

    @Enumerated(EnumType.STRING)
    @Column(name = "consumer_type")
    private ConsumerType consumerType;

    @Override
    public String toString() {
        return "ConsumerMaster{" +
                "consumerId=" + consumerId +
                ", consumerName='" + consumerName + '\'' +
                ", processId='" + processId + '\'' +
                ", description='" + description + '\'' +
                ", frequency='" + frequency + '\'' +
                ", consumerType=" + consumerType +
                '}';
    }
}
