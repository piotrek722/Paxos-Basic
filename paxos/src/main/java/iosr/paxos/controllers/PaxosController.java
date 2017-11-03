package iosr.paxos.controllers;

import iosr.paxos.model.messages.Message;
import iosr.paxos.services.acceptor.Acceptor;
import iosr.paxos.services.listener.Listener;
import iosr.paxos.services.proposer.Proposer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/paxos")
public class PaxosController {

    private Proposer proposer;
    private Acceptor acceptor;
    private Listener listener;

    public PaxosController(Proposer proposer, Acceptor acceptor, Listener listener) {
        this.proposer = proposer;
        this.acceptor = acceptor;
        this.listener = listener;
    }

    @PostMapping
    public ResponseEntity process(Message message, HttpServletRequest request){
        return ResponseEntity.ok().build();
    }



}
