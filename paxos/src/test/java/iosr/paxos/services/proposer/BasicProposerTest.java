package iosr.paxos.services.proposer;

import iosr.paxos.model.Entry;
import iosr.paxos.model.SequenceNumber;
import iosr.paxos.services.communication.ProposerCommunicationService;
import org.junit.Before;
import org.junit.Test;

import iosr.paxos.model.Data;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class BasicProposerTest {

    BasicProposer basicProposer = new BasicProposer(new ProposerCommunicationService(new RestTemplate()));
    private static  List<Data> promises = new LinkedList<>();



    @Before
    public void setProposer() {
        basicProposer.setClusterSize(5);
        promises.add(new Data(new SequenceNumber("serverName", 0), new Entry("", "")));
        promises.add(new Data(new SequenceNumber("serverName", 1), new Entry("", "")));
        promises.add(new Data(new SequenceNumber("serverName", 2), new Entry("", "")));
        promises.add(new Data(new SequenceNumber("serverName", 7), new Entry("did you know that", "cow jumps over the moon")));
        promises.add(new Data(new SequenceNumber("serverName", 5), new Entry("", "")));
        basicProposer.setPromises(promises);
    }


    @Test
    public void propose () throws Exception {
        basicProposer.propose("key", "value");
        assertEquals("value",basicProposer.getValue());
    }
    @Test
    public void shouldClearAfterQuorumCommit(){
        basicProposer.commit();
        assertNull( basicProposer.getValue());
        assertEquals(0, basicProposer.getPromises().size());
    }

    @Test
    public void handleProposeTest(){
        basicProposer.handlePrepare("key", "value");
        assertEquals("cow jumps over the moon", basicProposer.getValue());

    }

    @Test
    public void shouldFailWhenBetterPromised(){
        assertFalse(basicProposer.handlePrepare("key2","val2"));
        basicProposer.setPromises(promises);
    }

    @Test
    public void shouldReturnTrueWithNoBetterResponse(){
        basicProposer.setPromises(new LinkedList<>());
        assertTrue(basicProposer.handlePrepare("key", "val"));
    }



}