package iosr.paxos.services.communication;

import iosr.paxos.model.Data;
import iosr.paxos.model.SequenceNumber;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;


public class ProposerCommunicationService extends CommunicationService {
    private RestTemplate restTemplate;

    public ProposerCommunicationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Data> sendPromiseToAll(SequenceNumber sequenceNumber) {
        return getServersUrls().stream().map(serverUrl ->
                restTemplate.getForEntity(serverUrl + "/acceptor/proposal?sequenceNumber=" + sequenceNumber.toString(),
                        Data.class, sequenceNumber).getBody()
        ).collect(Collectors.toList());

    }
    public void sendAcceptToAll(Data data) {
        getServersUrls().forEach(serverUrl -> {
            restTemplate.postForLocation(serverUrl + "/acceptor/accept", data);
        });
    }
}

