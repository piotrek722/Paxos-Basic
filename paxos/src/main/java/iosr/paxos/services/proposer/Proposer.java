package iosr.paxos.services.proposer;

import iosr.paxos.model.Data;
import iosr.paxos.model.Entry;
import iosr.paxos.model.SequenceNumber;

import java.util.LinkedList;
import java.util.List;

public interface Proposer {

   // Boolean propose(String value);
    boolean propose(String key, String value);
    void commit();
    List<Data> getPromises();

}
