package iosr.paxos.services.communication;

import iosr.paxos.model.Data;
import iosr.paxos.model.Entry;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AcceptorCommunicationService extends CommunicationService {

    private RestTemplate restTemplate;

    public AcceptorCommunicationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void notifyListeners(Data accepted) {

        getServersUrls().forEach(serverUrl -> {
            restTemplate.postForLocation(serverUrl + "/listener", accepted);
        });

    }

}
