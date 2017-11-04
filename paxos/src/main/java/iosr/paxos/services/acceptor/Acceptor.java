package iosr.paxos.services.acceptor;

import iosr.paxos.model.Data;
import iosr.paxos.model.SequenceNumber;

public interface Acceptor {

    Data handlePrepareRequest(SequenceNumber sequenceNumber);

    void accept(Data data);
}
