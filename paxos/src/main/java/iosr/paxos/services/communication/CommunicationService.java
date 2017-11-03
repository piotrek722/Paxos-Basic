package iosr.paxos.services.communication;

import iosr.paxos.model.Data;
import iosr.paxos.model.messages.MessageType;

public interface CommunicationService{

    void sendToAll(Data data, MessageType messageType);

    void send(Data data, String address);
}
