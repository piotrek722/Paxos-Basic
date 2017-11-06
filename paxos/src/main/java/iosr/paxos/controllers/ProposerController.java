package iosr.paxos.controllers;

import iosr.paxos.services.proposer.Proposer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proposer")
public class ProposerController {

    private Proposer proposer;

    public ProposerController(Proposer proposer) {
        this.proposer = proposer;
    }

    @PostMapping("/propose")
    public ResponseEntity postValue(@RequestParam String key, @RequestParam String value) {
        proposer.propose(key, value);
        proposer.commit();
        return ResponseEntity.ok().build();
    }

}
