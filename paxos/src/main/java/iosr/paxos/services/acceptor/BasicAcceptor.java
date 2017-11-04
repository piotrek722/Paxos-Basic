package iosr.paxos.services.acceptor;

import iosr.paxos.model.Data;
import iosr.paxos.model.Entry;
import iosr.paxos.model.SequenceNumber;
import iosr.paxos.services.communication.AcceptorCommunicationService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BasicAcceptor implements Acceptor {

    private AcceptorCommunicationService communicationService;

    private SequenceNumber maxSequenceNumber;
    private Entry accepted;

    public BasicAcceptor(AcceptorCommunicationService communicationService) {
        this.communicationService = communicationService;
    }

    @Override
    public Data handlePrepareRequest(SequenceNumber sequenceNumber) {

        if (Objects.isNull(maxSequenceNumber) || sequenceNumber.getSequenceNumber() > maxSequenceNumber.getSequenceNumber()) {
            maxSequenceNumber = sequenceNumber;
        }
        return new Data(maxSequenceNumber, accepted);
    }

    @Override
    public void accept(Data data) {

        if (data.getSequenceNumber().equals(maxSequenceNumber)) {
            //can be accepted
            accepted = data.getValue();
            communicationService.notifyListeners(accepted);
        }
    }
}
