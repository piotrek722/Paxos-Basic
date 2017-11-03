package iosr.paxos.services.acceptor;

import iosr.paxos.model.Data;
import iosr.paxos.model.SequenceNumber;
import org.springframework.stereotype.Service;

@Service
public class BasicAcceptor implements Acceptor {

    private SequenceNumber maxSequenceNumber;
    private Data accepted;

    @Override
    public void handlePrepareRequest(Data data) {

    }

    @Override
    public void accept(Data data) {

    }
}
