package iosr.paxos.services.acceptor;

import iosr.paxos.model.Data;
import iosr.paxos.model.ProposeAnswer;
import iosr.paxos.model.SequenceNumber;

public interface Acceptor {

    ProposeAnswer handlePrepareRequest(SequenceNumber sequenceNumber);

    void accept(Data data);

    void clear(SequenceNumber sequenceNumber);
}
