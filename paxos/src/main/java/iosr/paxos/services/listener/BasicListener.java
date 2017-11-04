package iosr.paxos.services.listener;

import iosr.paxos.model.Entry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BasicListener implements Listener {

    private final Map<String, String> entryMap = new ConcurrentHashMap<>();
    private final Map<Entry, Integer> proposalMap = new ConcurrentHashMap<>();

    @Value("${cluster.size}")
    private int clusterSize;

    @Override
    public String get(String key) {
        return entryMap.get(key);
    }

    @Override
    public void proposeValue(Entry entry) {

        int entryCount = proposalMap.getOrDefault(entry, 0);
        entryCount++;

        if(isMajority(entryCount)){
            entryMap.put(entry.getKey(), entry.getValue());
            proposalMap.clear();
        }else{
            proposalMap.put(entry, entryCount);
        }
    }

    private boolean isMajority(int count) {
        return count >= (clusterSize / 2) + 1;
    }

    public Map<String, String> getEntryMap() {return entryMap;}
    public Map<Entry, Integer> getProposalMap() {return proposalMap;}
}
