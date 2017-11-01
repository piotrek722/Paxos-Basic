package iosr.paxos.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/test")
public class TestController {
    @GetMapping
    public String test(){
        return "test";
    }
}
