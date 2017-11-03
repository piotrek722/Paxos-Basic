package iosr.paxos.model;

public final class Data {

    private SequenceNumber sequenceNumber;
    private Entry value;

    public Data(SequenceNumber sequenceNumber){
        this(sequenceNumber, null);
    }

    public Data(SequenceNumber sequenceNumber, Entry value) {
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
