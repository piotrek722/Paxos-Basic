package iosr.paxos.services.proposer;

import iosr.paxos.model.Data;
import iosr.paxos.model.Entry;

import java.util.LinkedList;

public interface Proposer {

    Boolean propose(Entry value);

    void commit(Data data);
    LinkedList<Data> getPromises();
}
