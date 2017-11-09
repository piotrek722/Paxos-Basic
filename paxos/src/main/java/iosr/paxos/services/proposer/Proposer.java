package iosr.paxos.services.proposer;

import iosr.paxos.model.Entry;

public interface Proposer {

    boolean propose(Entry entry);
    void commit();
}
