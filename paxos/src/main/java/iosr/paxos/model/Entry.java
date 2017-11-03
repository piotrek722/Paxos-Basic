package iosr.paxos.model;

import java.io.Serializable;

public final class Entry implements Serializable{

    private String key;
    private String value;

    public Entry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
