package iosr.paxos.services.proposer;

import iosr.paxos.model.Data;

public interface Proposer {

    Boolean propose(String value);

    void commit(Data data);
}
