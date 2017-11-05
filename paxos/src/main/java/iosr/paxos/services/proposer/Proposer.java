package iosr.paxos.services.proposer;

import iosr.paxos.model.Data;
import iosr.paxos.model.Entry;
import iosr.paxos.model.SequenceNumber;

import java.util.LinkedList;

public interface Proposer {

    Boolean propose(SequenceNumber value);

    Boolean propose(String value);
    void commit(Data data);
    LinkedList<Data> getPromises();

}
