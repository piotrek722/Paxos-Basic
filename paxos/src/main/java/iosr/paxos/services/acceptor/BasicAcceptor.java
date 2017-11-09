package iosr.paxos.services.acceptor;

import iosr.paxos.model.Data;
import iosr.paxos.model.Entry;
import iosr.paxos.model.ProposeAnswer;
import iosr.paxos.model.SequenceNumber;
import iosr.paxos.services.communication.AcceptorCommunicationService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BasicAcceptor implements Acceptor {

    private AcceptorCommunicationService communicationService;

    private SequenceNumber maxSequenceNumber;
    private Data accepted;

    public BasicAcceptor(AcceptorCommunicationService communicationService) {
        this.communicationService = communicationService;
    }

    @Override
    public synchronized ProposeAnswer handlePrepareRequest(SequenceNumber sequenceNumber) {

        if (Objects.isNull(maxSequenceNumber) || sequenceNumber.getSeqNumber() > maxSequenceNumber.getSeqNumber()) {
            maxSequenceNumber = sequenceNumber;
        }
        return new ProposeAnswer(maxSequenceNumber, accepted);
    }

    @Override
    public synchronized void accept(Data data) {

        if (data.getSequenceNumber().equals(maxSequenceNumber)) {
            //can be accepted
            accepted = data;
            communicationService.notifyListeners(accepted);
        }
    }

    @Override
    public void clear(SequenceNumber sequenceNumberToClear) {
        if(!Objects.isNull(getAccepted())){
            SequenceNumber sequenceNumberOfAcceptedData = getAccepted().getSequenceNumber();
            if(sequenceNumberOfAcceptedData.getSeqNumber() <= sequenceNumberToClear.getSeqNumber()){
                this.accepted = null;
            }
        }
    }

    public Data getAccepted() {
        return accepted;
    }
}
