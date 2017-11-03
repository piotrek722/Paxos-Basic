package iosr.paxos.services.communication;

import iosr.paxos.model.Data;
import iosr.paxos.model.messages.MessageType;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class HttpCommunicationService implements CommunicationService {

    private Set<String> serverAddresses = new HashSet<>();

    @Override
    public void sendToAll(Data data, MessageType messageType) {

    }

    @Override
    public void send(Data data, String address) {

    }
}
