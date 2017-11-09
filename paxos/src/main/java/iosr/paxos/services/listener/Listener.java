package iosr.paxos.services.listener;

import iosr.paxos.model.Data;
import iosr.paxos.model.Entry;

public interface Listener {

    String get(String key);

    void handleAcceptedData(Data data);
}
