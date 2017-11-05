package iosr.paxos.controllers;

import iosr.paxos.model.Entry;
import iosr.paxos.services.proposer.Proposer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import iosr.paxos.model.Data;
import iosr.paxos.services.proposer.BasicProposer;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/proposer")
public class ProposerController {

    private Proposer proposer;
    public ProposerController(Proposer proposer){
        this.proposer=proposer;

    }
    @PostMapping
    private ResponseEntity sendValue(@RequestBody Entry entry){
        proposer.propose(entry);
        return ResponseEntity.ok().build();
    }

}
