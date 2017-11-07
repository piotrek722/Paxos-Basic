package iosr.paxos.services.proposer;

import iosr.paxos.model.Data;
import iosr.paxos.model.Entry;
import iosr.paxos.model.SequenceNumber;
import iosr.paxos.services.communication.ProposerCommunicationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class BasicProposer implements Proposer {

    private ProposerCommunicationService communicationService;
    private SequenceNumber sequenceNumber;

    private String serverName;
    private String key;
    private String value;
    private List<Data> promises = new LinkedList<>();

    @Value("${cluster.size}")
    private int clusterSize;

    public BasicProposer(ProposerCommunicationService communicationService) {
        this.communicationService = communicationService;
        sequenceNumber = new SequenceNumber(this.serverName, 0);
    }

    @Override
    public boolean propose(String key, String value){
        //issue a prepare request
        //get the best response of promised
        //if quorum promised, issue a commit (accept request), with old seqNr
        boolean propose= prepare(key, value);
        if(isQuorum()) commit();
        return propose;
    }

    public boolean prepare(String key, String value){
        promises=communicationService.sendPromiseToAll(sequenceNumber);
        return handlePrepare(key, value);
    }

    public boolean handlePrepare(String key, String value){
        this.value = value;
        this.key = key;
        int bestSequenceNumber = this.sequenceNumber.getSeqNumber();
        String bestValue=null;
        boolean result= true;
        for (Data promise :promises){
            if(promise.getSequenceNumber().getSeqNumber() !=null && promise.getSequenceNumber().getSeqNumber()>bestSequenceNumber){
                result=false;
                bestSequenceNumber=promise.getSequenceNumber().getSeqNumber();
                bestValue=promise.getValue().getValue();
            }
        }
        if (bestSequenceNumber>this.sequenceNumber.getSeqNumber()){
            this.value= (bestValue==null) ? this.value : bestValue;
           // this.sequenceNumber.setSeqNumber(bestSequenceNumber);
        }
        return result;
    }

    @Override
    public void commit() {
        communicationService.sendAcceptToAll(new Data(sequenceNumber, new Entry(key, value)));
        clear();
    }

    private Boolean isQuorum() {
        return promises.size() >= clusterSize / 2 + 1;
    }

    private void clear() {
        sequenceNumber.setSeqNumber(0);
        promises.clear();
        value = null;
        key = null;
    }

    //for tests:
    public List<Data> getPromises() {
        return promises;
    }

    public void setPromises(List<Data> promises) {
        this.promises = promises;
    }

    void setClusterSize(int clusterSize) {
        this.clusterSize = clusterSize;
    }

    public String getValue() {
        return value;
    }
}




