package iosr.paxos.services.proposer;

import iosr.paxos.model.Data;
import iosr.paxos.model.Entry;
import iosr.paxos.model.SequenceNumber;
import iosr.paxos.services.communication.ProposerCommunicationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class BasicProposer implements Proposer {

    private ProposerCommunicationService communicationService;
    private SequenceNumber sequenceNumber;

    @Value("${cluster.size}")
     int clusterSize;

    @Value("${server.name}")
    private String serverName;

    private Entry clientEntry;

    public BasicProposer(ProposerCommunicationService communicationService) {
        this.communicationService = communicationService;
    }

    @PostConstruct
    private void init(){
        sequenceNumber = new SequenceNumber(this.serverName, 0);
    }

    @Override
    public boolean propose(Entry entry) {
        this.clientEntry = entry;
        this.sequenceNumber.incrementSeqNumberByOne();

        List<Data> promises = communicationService.sendPromiseToAll(sequenceNumber);
        boolean isValueNotChanged = handlePromises(promises);

        if (isQuorum(promises)) {
            commit();
            return isValueNotChanged;
        }

        return false;
    }

    @Override
    public void commit() {
        communicationService.sendAcceptToAll(new Data(sequenceNumber, clientEntry));
    }

     boolean handlePromises(List<Data> promises) {

        Optional<Entry> acceptedEntry = promises.stream()
                .max(Comparator.comparingInt(promise -> promise.getSequenceNumber().getSeqNumber()))
                .map(Data::getValue);

        if(acceptedEntry.isPresent()){
            this.clientEntry = acceptedEntry.get();
            return false;
        }

        return true;

    }

     Boolean isQuorum(List<Data> promises) {
        long count = promises.stream()
                .map(Data::getSequenceNumber)
                .map(SequenceNumber::getSeqNumber)
                .filter(sequenceNumber -> sequenceNumber.equals(this.sequenceNumber.getSeqNumber()))
                .count();
        return count >= (clusterSize / 2) + 1;
    }

    public SequenceNumber getSequenceNumber() {
        return sequenceNumber;
    }

    public Entry getClientEntry() {
        return clientEntry;
    }

    public void setSequenceNumber(SequenceNumber sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

}




