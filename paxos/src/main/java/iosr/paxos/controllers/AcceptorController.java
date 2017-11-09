package iosr.paxos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import iosr.paxos.model.Data;
import iosr.paxos.model.ProposeAnswer;
import iosr.paxos.model.SequenceNumber;
import iosr.paxos.services.acceptor.Acceptor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/acceptor")
public class AcceptorController {

    private Acceptor acceptor;

    public AcceptorController(Acceptor acceptor) {
        this.acceptor = acceptor;
    }

    // /acceptor/proposal?sequenceNumber={"serverName": "lol", "seqNumber": 1}
    @GetMapping("/proposal")
    private ResponseEntity<ProposeAnswer> getProposal(@RequestParam String sequenceNumber) throws IOException {
        SequenceNumber number = new ObjectMapper().readValue(sequenceNumber, SequenceNumber.class);
        ProposeAnswer acceptorAnswer = acceptor.handlePrepareRequest(number);
        return ResponseEntity.ok(acceptorAnswer);
    }

    @PostMapping("/accept")
    private ResponseEntity accept(@RequestBody Data data) {
        acceptor.accept(data);
        return ResponseEntity.ok().build();
    }
}
