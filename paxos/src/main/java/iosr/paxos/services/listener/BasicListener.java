package iosr.paxos.services.listener;

import iosr.paxos.model.Data;
import iosr.paxos.model.Entry;
import iosr.paxos.model.SequenceNumber;
import iosr.paxos.services.acceptor.Acceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class BasicListener implements Listener {

    private final Map<String, String> entryMap = new HashMap<>();
    private final Map<Entry, Integer> proposalMap = new HashMap<>();

    @Value("${cluster.size}")
    private int clusterSize;
    private SequenceNumber threshold;

    private Acceptor acceptor;

    public BasicListener(Acceptor acceptor) {
        this.acceptor = acceptor;
    }

    @Override
    public String get(String key) {
        return entryMap.get(key);
    }

    @Override
    public synchronized void handleAcceptedData(Data data) {

        SequenceNumber acceptedSequenceNumber = data.getSequenceNumber();
        if(!Objects.isNull(getThreshold()) && isSequenceNumberLessOrEqualThanThreshold(acceptedSequenceNumber)){
            return;
        }

        Entry entry = data.getValue();
        int entryCount = proposalMap.getOrDefault(entry, 0);
        entryCount++;

        if(isMajority(entryCount)){
            //insert value
            entryMap.put(entry.getKey(), entry.getValue());

            //clear map and instance
            proposalMap.clear();
            clearInstance(acceptedSequenceNumber);
        }else{
            proposalMap.put(entry, entryCount);
        }
    }

    private boolean isSequenceNumberLessOrEqualThanThreshold(SequenceNumber sequenceNumber){
        return sequenceNumber.getSeqNumber() <= getThreshold().getSeqNumber();
    }

    private void clearInstance(SequenceNumber sequenceNumber){
        this.threshold = sequenceNumber;
        acceptor.clear(threshold);
    }

    private boolean isMajority(int count) {
        return count >= (clusterSize / 2) + 1;
    }

    public Map<String, String> getEntryMap() {return entryMap;}
    public Map<Entry, Integer> getProposalMap() {return proposalMap;}
    public SequenceNumber getThreshold() {return threshold;}
}
