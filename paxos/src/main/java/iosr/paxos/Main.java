package iosr.paxos;

import iosr.paxos.services.communication.ProposerCommunicationService;
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
    @Bean
    public ProposerCommunicationService proposerCommunicationService(){
        return new ProposerCommunicationService(restTemplate());
    }
}
