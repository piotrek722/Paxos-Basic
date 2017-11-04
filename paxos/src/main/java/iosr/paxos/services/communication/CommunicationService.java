package iosr.paxos.services.communication;

import org.springframework.beans.factory.annotation.Value;

import java.util.HashSet;
import java.util.Set;

public abstract class CommunicationService {

    @Value("#{'${servers.urls}'.split(',')}")
    private Set<String> serversUrls = new HashSet<>();

    protected Set<String> getServersUrls() {
        return serversUrls;
    }
}
