package iosr.paxos.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public final class SequenceNumber implements Serializable {

    private String serverName;
    private Integer sequenceNumber;

    @JsonCreator
    public SequenceNumber(@JsonProperty("serverName") String serverName,
                          @JsonProperty("sequenceNumber") Integer sequenceNumber) {
        this.serverName = serverName;
        this.sequenceNumber = sequenceNumber;
    }

    public String getServerName() {
        return serverName;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }
}
