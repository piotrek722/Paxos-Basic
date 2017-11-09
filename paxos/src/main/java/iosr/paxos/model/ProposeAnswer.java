package iosr.paxos.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class ProposeAnswer {

    private SequenceNumber sequenceNumber;
    private Data lastAcceptedData;

    @JsonCreator
    public ProposeAnswer(@JsonProperty("sequenceNumber") SequenceNumber sequenceNumber,
                         @JsonProperty("lastAcceptedData") Data lastAcceptedData) {
        this.sequenceNumber = sequenceNumber;
        this.lastAcceptedData = lastAcceptedData;
    }

    public SequenceNumber getSequenceNumber() {
        return sequenceNumber;
    }

    public Data getLastAcceptedData() {
        return lastAcceptedData;
    }
}
