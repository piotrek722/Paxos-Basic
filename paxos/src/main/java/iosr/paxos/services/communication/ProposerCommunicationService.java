package iosr.paxos.services.communication;

import iosr.paxos.model.Data;
import iosr.paxos.model.SequenceNumber;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProposerCommunicationService extends CommunicationService {
    private RestTemplate restTemplate;

    public ProposerCommunicationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Data> sendPromiseToAll(SequenceNumber sequenceNumber) {

       return getServersUrls().stream().map(serverUrl -> {
           try {
               return restTemplate
                               .postForEntity(serverUrl + "/acceptor/proposal?sequenceNumber", sequenceNumber, Data.class)
                               .getBody();
           } catch (HttpStatusCodeException exception) {
               return null;
           }
       })
               .filter(Objects::nonNull).collect(Collectors.toList());
    }

    public void sendAcceptToAll(Data data) {
        getServersUrls().forEach(serverUrl -> restTemplate.postForLocation(serverUrl + "/acceptor/accept", data));
    }
}

