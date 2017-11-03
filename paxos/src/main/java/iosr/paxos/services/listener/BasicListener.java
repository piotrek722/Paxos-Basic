package iosr.paxos.services.listener;

import iosr.paxos.model.Entry;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BasicListener implements Listener {

    // Maybe ConcurrentHashMap
    private Map<String, String> entryMap = new HashMap<>();

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public void proposeValue(Entry entry) {

    }
}
