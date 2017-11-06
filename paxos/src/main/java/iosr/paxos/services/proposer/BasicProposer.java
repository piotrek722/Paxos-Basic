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
    private Data bestPromisedData;

    private String serverName;
    private String key;
    private String value;
    private List<Data> promises = new LinkedList<>();

    @Value("${cluster.size}")
    private int clusterSize;

    BasicProposer(ProposerCommunicationService communicationService) {
        this.communicationService = communicationService;
        this.serverName = "serverName";
        sequenceNumber = new SequenceNumber(this.serverName, 0);
        bestPromisedData = new Data(sequenceNumber, new Entry(this.key, this.value));
        this.value = "";
    }

    @Override
    public boolean propose(String key, String value) {
        this.value = value;
        this.key = key;
        bestPromisedData = new Data(new SequenceNumber(serverName, 0), new Entry(key, value));
        promises = communicationService.sendPromiseToAll(sequenceNumber);
        return true;
    }

    @Override
    public void commit() {
        handleCommit();
        if (isQuorum()) clear();
    }

    void handleCommit() {
        bestPromisedData = comparePromiseSequenceNumber(promises);
        if (isQuorum()) {
            communicationService.sendAcceptToAll(bestPromisedData);
        }
    }

    private Boolean isQuorum() {
        return promises.size() >= clusterSize / 2 + 1;
    }

    private void clear() {
        sequenceNumber.setSeqNumber(0);
        promises.clear();
        value = "";
        key = "";
        bestPromisedData = new Data(new SequenceNumber(serverName, 0), new Entry("", ""));
    }

    private Data comparePromiseSequenceNumber(List<Data> promisesList) {
        for (Data promise : promisesList) {
            //TODO probably remove this null check?? Or check this earlier or sth
            if (promise.getSequenceNumber().getSeqNumber() != null && promise.getSequenceNumber().getSeqNumber() > bestPromisedData.getSequenceNumber().getSeqNumber()) {
                bestPromisedData = promise;
            }
        }
        return bestPromisedData;
    }

    //for tests:
    public List<Data> getPromises() {
        return promises;
    }

    Data getBestPromisedData() {
        return bestPromisedData;
    }

    void setClusterSize(int clusterSize) {
        this.clusterSize = clusterSize;
    }

    public String getValue() {
        return value;
    }
}




