package iosr.paxos.model.messages;

import iosr.paxos.model.Data;

public final class Message {

    private Data data;
    private MessageType messageType;

    public Message(Data data, MessageType messageType) {
        this.data = data;
        this.messageType = messageType;
    }

    public Data getData() {
        return data;
    }

    public MessageType getMessageType() {
        return messageType;
    }
}
