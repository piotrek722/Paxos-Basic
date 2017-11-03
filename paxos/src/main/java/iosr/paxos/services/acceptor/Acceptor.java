package iosr.paxos.services.acceptor;

import iosr.paxos.model.Data;

public interface Acceptor {

    void handlePrepareRequest(Data data);

    void accept(Data data);
}
