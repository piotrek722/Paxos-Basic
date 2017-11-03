package iosr.paxos.services.proposer;

import iosr.paxos.model.Data;
import iosr.paxos.model.SequenceNumber;
import org.springframework.stereotype.Service;

@Service
public class BasicProposer implements Proposer {

    private SequenceNumber sequenceNumber;
    private String value;

    @Override
    public Boolean propose(String value) {
        return null;
    }

    @Override
    public void commit(Data data) {

    }
}
