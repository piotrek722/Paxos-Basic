package iosr.paxos.model;

import java.io.Serializable;

public final class SequenceNumber implements Serializable {

    private String serverName;
    private Integer sequenceNumber;

    public SequenceNumber(String serverName, Integer sequenceNumber) {
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
