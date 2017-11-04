package iosr.paxos.controllers;

import iosr.paxos.model.Data;
import iosr.paxos.model.Entry;
import iosr.paxos.services.listener.Listener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/listener")
public class ListenerController {

    private Listener listener;

    public ListenerController(Listener listener) {
        this.listener = listener;
    }

    @GetMapping("/{key}")
    private ResponseEntity<String> getValue(@PathVariable String key){
        String value = listener.get(key);
        return ResponseEntity.ok(value);
    }

    @PostMapping
    private ResponseEntity commitValue(@RequestBody Entry entry){
        listener.proposeValue(entry);
        return ResponseEntity.ok().build();
    }
}
