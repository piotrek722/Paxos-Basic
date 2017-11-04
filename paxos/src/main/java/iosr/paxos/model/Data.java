package iosr.paxos.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public final class Data implements Serializable{

    private SequenceNumber sequenceNumber;
    private Entry value;

    @JsonCreator
    public Data(@JsonProperty("sequenceNumber") SequenceNumber sequenceNumber,
                @JsonProperty("value") Entry value) {
        this.sequenceNumber = sequenceNumber;
        this.value = value;
    }

    public SequenceNumber getSequenceNumber() {
        return sequenceNumber;
    }

    public Entry getValue() {
        return value;
    }
}
