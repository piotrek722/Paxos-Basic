package iosr.paxos.services.proposer;

import iosr.paxos.model.Data;
import iosr.paxos.model.Entry;
import iosr.paxos.model.SequenceNumber;
import iosr.paxos.services.communication.ProposerCommunicationService;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class BasicProposerTest {

    private BasicProposer basicProposer = new BasicProposer(new ProposerCommunicationService(new RestTemplate()));
    private static final Data DATA = new Data(new SequenceNumber("", 0),new Entry(null, null));
    @Test
    public void testIsQuorum(){
        basicProposer.clusterSize=5;
        basicProposer.setSequenceNumber(new SequenceNumber("", 0));
        assertTrue(basicProposer.isQuorum(Arrays.asList(DATA, DATA, DATA)));
    }
    @Test
    public void testIsNotQuorum(){
        basicProposer.clusterSize=5;
        basicProposer.setSequenceNumber(new SequenceNumber("", 0));
        assertFalse(basicProposer.isQuorum(Arrays.asList(DATA, DATA)));
    }

    @Test
    public void testHandlePromisesNotNullEntryForTheHighestSequenceNumber(){
        Entry entry = new Entry("123","456");
        List<Data> promises = Arrays.asList(DATA, new Data(new SequenceNumber("", 1),entry));
        assertFalse(basicProposer.handlePromises(promises));
        assertEquals(basicProposer.getClientEntry(),entry );
    }

    @Test
    public void testHandlePromisesNullEntry(){
        List<Data> promises = Arrays.asList(DATA, new Data(new SequenceNumber("", 1),null));
        assertTrue(basicProposer.handlePromises(promises));
    }



}