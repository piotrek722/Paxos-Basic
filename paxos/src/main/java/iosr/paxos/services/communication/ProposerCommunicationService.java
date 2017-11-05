package iosr.paxos.services.communication;

import iosr.paxos.model.SequenceNumber;
import org.springframework.web.client.RestTemplate;
import iosr.paxos.model.Data;
import iosr.paxos.model.Entry;


public class ProposerCommunicationService extends CommunicationService {
    private RestTemplate restTemplate;

    public ProposerCommunicationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void sendPromiseToAll(Data data) {
        getServersUrls().forEach(serverUrl -> {
            restTemplate.postForLocation(serverUrl + "/acceptor/proposal", data);
        });
    }

    public void sendAcceptToAll(Data data)    {
        getServersUrls().forEach(serverUrl -> {
            restTemplate.postForLocation(serverUrl + "/acceptor/accept", data);
        });
    }
    }

