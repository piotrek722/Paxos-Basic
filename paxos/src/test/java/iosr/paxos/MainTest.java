package iosr.paxos;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

    private Main main = new Main();

    @Test
    public void getText() throws Exception {
        assertEquals("Check2", main.getText());
    }

}