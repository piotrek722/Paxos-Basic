package iosr.paxos;

import iosr.paxos.services.acceptor.Acceptor;
import iosr.paxos.services.acceptor.BasicAcceptor;
import iosr.paxos.services.communication.AcceptorCommunicationService;
import iosr.paxos.services.communication.ProposerCommunicationService;
import iosr.paxos.services.proposer.BasicProposer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


}
