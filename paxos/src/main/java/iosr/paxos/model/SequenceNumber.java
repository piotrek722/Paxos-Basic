package iosr.paxos.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public final class SequenceNumber implements Serializable {

    private String serverName;
    private Integer seqNumber;

    @JsonCreator
    public SequenceNumber(@JsonProperty("serverName") String serverName,
                          @JsonProperty("seqNumber") Integer seqNumber) {
        this.serverName = serverName;
        this.seqNumber = seqNumber;
    }

    public String getServerName() {
        return serverName;
    }

    public Integer getSeqNumber() {
        return seqNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SequenceNumber that = (SequenceNumber) o;

        if (serverName != null ? !serverName.equals(that.serverName) : that.serverName != null) return false;
        return seqNumber != null ? seqNumber.equals(that.seqNumber) : that.seqNumber == null;
    }

    @Override
    public int hashCode() {
        int result = serverName != null ? serverName.hashCode() : 0;
        result = 31 * result + (seqNumber != null ? seqNumber.hashCode() : 0);
        return result;
    }

    public void setSeqNumber(int value) {
        this.seqNumber = value;
    }

    @Override
    public String toString() {
        return "{\"serverName\": \"" + serverName + "\", \"seqNumber\": " + seqNumber + "}";
    }
}
