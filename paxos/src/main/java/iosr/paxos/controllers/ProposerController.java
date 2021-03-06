package iosr.paxos.controllers;

import iosr.paxos.model.Entry;
import iosr.paxos.services.proposer.Proposer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proposer")
public class ProposerController {

    private Proposer proposer;

    public ProposerController(Proposer proposer) {
        this.proposer = proposer;
    }

    @PostMapping("/propose")
    public ResponseEntity postValue(@RequestBody Entry entry) {
        boolean isAdded = proposer.propose(entry);
        return ResponseEntity.ok(isAdded);
    }

}
